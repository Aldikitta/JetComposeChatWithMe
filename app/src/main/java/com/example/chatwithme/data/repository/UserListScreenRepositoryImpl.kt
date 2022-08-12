package com.example.chatwithme.data.repository

import com.example.chatwithme.core.Constants.ERROR_MESSAGE
import com.example.chatwithme.core.Constants.NO_CHATROOM_IN_FIREBASE_DATABASE
import com.example.chatwithme.domain.model.*
import com.example.chatwithme.domain.repository.UserListScreenRepository
import com.example.chatwithme.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.onesignal.OneSignal
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.HashMap

@Singleton
class UserListScreenRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase,
) : UserListScreenRepository {
    override suspend fun loadAcceptedFriendRequestListFromFirebase(): Flow<Response<List<FriendListRow>>> =
        callbackFlow {
            try {
                val myUUID = auth.currentUser?.uid
                val databaseReference = database.getReference("Friend_List")

                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val friendListRegisterAcceptedList = mutableListOf<FriendListRegister>()
                        launch {
                            for (i in snapshot.children) {
                                val friendListRegister = i.getValue(FriendListRegister::class.java)

                                if (friendListRegister?.status == FriendStatus.ACCEPTED.toString()) {
                                    friendListRegisterAcceptedList += friendListRegister
                                }
                            }
                            launch {
                                val friendListUiRowList = mutableListOf<FriendListRow>()

                                for (i in friendListRegisterAcceptedList) {
                                    val pictureUrl: String = ""
                                    val chatRoomUID: String = i.chatRoomUUID
                                    val registerUUID: String = i.registerUUID
                                    val lastMessage: ChatMessage = i.lastMessage

                                    var email: String = ""
                                    var uuid: String = ""
                                    var oneSignalUserId: String = ""

                                    if (i.requesterUUID == myUUID) {
                                        email = i.acceptorEmail
                                        uuid = i.acceptorUUID
                                        oneSignalUserId = i.acceptorOneSignalUserId
                                    } else if (i.acceptorUUID == myUUID) {
                                        email = i.requesterEmail
                                        uuid = i.requesterUUID
                                        oneSignalUserId = i.requesterOneSignalUserId
                                    }

                                    if (email != "" && uuid != "") {
                                        val friendListRow = FriendListRow(
                                            chatRoomUID,
                                            email,
                                            uuid,
                                            oneSignalUserId,
                                            registerUUID,
                                            pictureUrl,
                                            lastMessage
                                        )
                                        friendListUiRowList += friendListRow
                                    }
                                }
                                launch {
                                    val resultList = mutableListOf<FriendListRow>()

                                    val asyncTask = async {
                                        for (i in friendListUiRowList) {
                                            database
                                                .getReference("Profiles")
                                                .child(i.userUUID)
                                                .child("profile")
                                                .child("userProfilePictureUrl")
                                                .get().addOnSuccessListener {
                                                    val friendListUiRow = FriendListRow(
                                                        i.chatRoomUUID,
                                                        i.userEmail,
                                                        i.userUUID,
                                                        i.oneSignalUserId,
                                                        i.registerUUID,
                                                        if (it.value != null) it.value as String else "",
                                                        i.lastMessage
                                                    )
                                                    resultList += friendListUiRow
                                                }.addOnFailureListener {
                                                    this@callbackFlow.trySendBlocking(
                                                        Response.Error(
                                                            it.localizedMessage ?: ERROR_MESSAGE
                                                        )
                                                    )
                                                }
                                        }
                                        delay(1000)
                                    }
                                    asyncTask.invokeOnCompletion {
                                        this@callbackFlow.trySendBlocking(
                                            Response.Success(
                                                resultList
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        this@callbackFlow.trySendBlocking(Response.Error(error.message))
                    }
                })
                awaitClose {
                    channel.close()
                    cancel()
                }
            } catch (e: Exception) {
                this@callbackFlow.trySendBlocking(Response.Error(e.message ?: ERROR_MESSAGE))
            }
        }

    override suspend fun loadPendingFriendRequestListFromFirebase(): Flow<Response<List<FriendListRegister>>> =
        callbackFlow {
            val myUUID = auth.currentUser?.uid

            val databaseReference = database.getReference("Friend_List")

            val postListener = databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        var resultList = listOf<FriendListRegister>()
                        for (i in snapshot.children) {
                            val friendListRegister = i.getValue(FriendListRegister::class.java)
                            if (friendListRegister?.status == FriendStatus.PENDING.toString() && friendListRegister.acceptorUUID == myUUID) {
                                resultList = resultList + friendListRegister
                            }
                        }
                        this@callbackFlow.trySendBlocking(Response.Success(resultList))
                    } catch (e: Exception) {
                        this@callbackFlow.trySendBlocking(
                            Response.Error(
                                e.message ?: ERROR_MESSAGE
                            )
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySendBlocking(Response.Error(error.message))
                }
            })
            databaseReference.addValueEventListener(postListener)
            awaitClose {
                databaseReference.removeEventListener(postListener)
                channel.close()
                cancel()
            }
        }

    override suspend fun searchUserFromFirebase(userEmail: String): Flow<Response<User?>> =
        callbackFlow {
            try {

                this@callbackFlow.trySendBlocking(Response.Loading)

                val databaseReference = database.getReference("Profiles")

                var user: User?

                databaseReference.get().addOnSuccessListener {
                    var flagForControl = false

                    val myJob = launch {
                        for (i in it.children) {
                            user = i.child("profile").getValue(User::class.java)!!
                            if (user?.userEmail == userEmail) {
                                flagForControl = true
                                this@callbackFlow.trySendBlocking(Response.Success(user))
                            }
                        }
                    }

                    myJob.invokeOnCompletion {
                        if (!flagForControl) {
                            this@callbackFlow.trySendBlocking(Response.Success(null))
                        }
                    }

                }.addOnFailureListener {
                    this@callbackFlow.trySendBlocking(Response.Error(it.message ?: ERROR_MESSAGE))
                }

                awaitClose {
                    channel.close()
                    cancel()
                }

            } catch (e: Exception) {
                this@callbackFlow.trySendBlocking(Response.Error(e.message ?: ERROR_MESSAGE))
            }
        }

    override suspend fun checkChatRoomExistedFromFirebase(acceptorUUID: String): Flow<Response<String>> =
        callbackFlow {
            try {
                this@callbackFlow.trySendBlocking(Response.Loading)

                val requesterUUID = auth.currentUser?.uid

                val hashMapOfRequesterUUIDAndAcceptorUUID = hashMapOf<String, String>()
                hashMapOfRequesterUUIDAndAcceptorUUID[requesterUUID!!] = acceptorUUID

                val hashMapOfAcceptorUUIDAndRequesterUUID = hashMapOf<String, String>()
                hashMapOfAcceptorUUIDAndRequesterUUID[acceptorUUID] = requesterUUID

                val gson = Gson()
                val requesterUUIDAndAcceptorUUID =
                    gson.toJson(hashMapOfRequesterUUIDAndAcceptorUUID)
                val acceptorUUIDAndRequesterUUID =
                    gson.toJson(hashMapOfAcceptorUUIDAndRequesterUUID)

                val databaseReference = database.getReference("Chat_Rooms")

                databaseReference.get().addOnSuccessListener {
                    try {
                        var keyListForControl = listOf<String>()
                        val hashMapForControl = hashMapOf<String, Any>()
                        for (i in it.children) {
                            val key = i.key as String
                            keyListForControl = keyListForControl + key
                            val hashMap: Map<String, Any> = Gson().fromJson(
                                i.key,
                                object : TypeToken<HashMap<String?, Any?>?>() {}.type
                            )

                            hashMapForControl.putAll(hashMap)
                        }

                        val chatRoomUUIDString: String?

                        if (keyListForControl.contains(requesterUUIDAndAcceptorUUID)) {

                            //ChatRoom opened by Requester
                            val hashMapOfRequesterUUIDAndAcceptorUUIDForSaveMessagesToFirebase =
                                hashMapOf<String, Any>()
                            hashMapOfRequesterUUIDAndAcceptorUUIDForSaveMessagesToFirebase[requesterUUID] =
                                acceptorUUID

                            val gson = Gson()
                            chatRoomUUIDString = gson.toJson(
                                hashMapOfRequesterUUIDAndAcceptorUUIDForSaveMessagesToFirebase
                            )

                            this@callbackFlow.trySendBlocking(Response.Success(chatRoomUUIDString!!))

                        } else if (keyListForControl.contains(acceptorUUIDAndRequesterUUID)) {

                            //ChatRoom opened by Acceptor
                            val hashMapOfAcceptorUUIDAndRequesterUUIDForSaveMessagesToFirebase =
                                hashMapOf<String, Any>()
                            hashMapOfAcceptorUUIDAndRequesterUUIDForSaveMessagesToFirebase[acceptorUUID] =
                                requesterUUID

                            val gson = Gson()
                            chatRoomUUIDString = gson.toJson(
                                hashMapOfAcceptorUUIDAndRequesterUUIDForSaveMessagesToFirebase
                            )

                            this@callbackFlow.trySendBlocking(Response.Success(chatRoomUUIDString!!))

                        } else {
                            this@callbackFlow.trySendBlocking(
                                Response.Success(
                                    NO_CHATROOM_IN_FIREBASE_DATABASE
                                )
                            )
                        }
                    } catch (e: JsonSyntaxException) {
                        this@callbackFlow.trySendBlocking(
                            Response.Error(
                                e.message ?: ERROR_MESSAGE
                            )
                        )
                    }
                }

                awaitClose {
                    channel.close()
                    cancel()
                }

            } catch (e: Exception) {
                this@callbackFlow.trySendBlocking(Response.Error(e.message ?: ERROR_MESSAGE))
            }
        }

    override suspend fun createChatRoomToFirebase(acceptorUUID: String): Flow<Response<String>> =
        flow {
            try {
                emit(Response.Loading)

                val requesterUUID = auth.currentUser?.uid

                val hashMapOfRequesterUUIDAndAcceptorUUID = hashMapOf<String, String>()
                hashMapOfRequesterUUIDAndAcceptorUUID[requesterUUID!!] = acceptorUUID

                val databaseReference = database.getReference("Chat_Rooms")

                val gson = Gson()
                val requesterUUIDAndAcceptorUUID =
                    gson.toJson(hashMapOfRequesterUUIDAndAcceptorUUID)

                databaseReference
                    .child(requesterUUIDAndAcceptorUUID)
                    .setValue(true)
                    .await()

                emit(Response.Success(requesterUUIDAndAcceptorUUID))

            } catch (e: Exception) {
                emit(Response.Error(e.message ?: ERROR_MESSAGE))
            }
        }

    override suspend fun checkFriendListRegisterIsExistedFromFirebase(
        acceptorEmail: String,
        acceptorUUID: String
    ): Flow<Response<FriendListRegister>> = callbackFlow {
        try {
            this@callbackFlow.trySendBlocking(Response.Loading)
            val requesterUUID = auth.currentUser?.uid
            val databaseReference = database.getReference("Friend_List")

            databaseReference.get().addOnSuccessListener {
                var result = FriendListRegister()

                val job = launch {
                    for (i in it.children) {
                        val friendListRegister = i.getValue(FriendListRegister::class.java)
                        if (friendListRegister?.requesterUUID == requesterUUID && friendListRegister?.acceptorUUID == acceptorUUID) {
                            result = friendListRegister
                        } else if (friendListRegister?.requesterUUID == acceptorUUID && friendListRegister.acceptorUUID == requesterUUID) {
                            result = friendListRegister
                        }
                    }
                }

                job.invokeOnCompletion {
                    this@callbackFlow.trySendBlocking(Response.Success(result))
                }
            }

            awaitClose {
                channel.close()
                cancel()
            }

        } catch (e: Exception) {
            this@callbackFlow.trySendBlocking(Response.Error(e.message ?: "Error Message"))
        }
    }

    override suspend fun createFriendListRegisterToFirebase(
        chatRoomUUID: String,
        acceptorEmail: String,
        acceptorUUID: String,
        acceptorOneSignalUserId: String
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)

            val registerUUID = UUID.randomUUID().toString()

            val requesterEmail = auth.currentUser?.email
            val requesterUUID = auth.currentUser?.uid
            val requesterOneSignalUserId = OneSignal.getDeviceState()?.userId

            val databaseReference = database.getReference("Friend_List")

            val friendListRegister =
                FriendListRegister(
                    chatRoomUUID,
                    registerUUID,
                    requesterEmail!!,
                    requesterUUID!!,
                    requesterOneSignalUserId!!,
                    acceptorEmail,
                    acceptorUUID,
                    acceptorOneSignalUserId,
                    FriendStatus.PENDING.toString(),
                    ChatMessage()
                )

            databaseReference
                .child(registerUUID)
                .setValue(friendListRegister)
                .await()

            emit(Response.Success(true))

        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun acceptPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>> =
        callbackFlow {
            try {
                this@callbackFlow.trySendBlocking(Response.Loading)

                val databaseReference =
                    database.getReference("Friend_List").child(registerUUID)

                val childUpdates = mutableMapOf<String, Any>()
                childUpdates["/status/"] = FriendStatus.ACCEPTED.toString()

                databaseReference.updateChildren(childUpdates).addOnSuccessListener {
                    this@callbackFlow.trySendBlocking(Response.Success(true))
                }.addOnFailureListener {
                    this@callbackFlow.trySendBlocking(Response.Success(false))
                }

            } catch (e: Exception) {
                this@callbackFlow.trySendBlocking(Response.Error(e.message ?: ERROR_MESSAGE))
            }

            awaitClose {
                channel.close()
                cancel()
            }
        }

    override suspend fun rejectPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>> =
        flow {
            try {
                emit(Response.Loading)
                database.getReference("Friend_List").child(registerUUID).setValue(null)
                    .await()
                emit(Response.Success(true))

            } catch (e: Exception) {
                emit(Response.Error(e.message ?: ERROR_MESSAGE))
            }
        }

    override suspend fun openBlockedFriendToFirebase(registerUUID: String): Flow<Response<Boolean>> =
        callbackFlow {
            try {
                this@callbackFlow.trySendBlocking(Response.Loading)

                val myUUID = auth.currentUser?.uid

                val databaseReference =
                    database.getReference("Friend_List").child(registerUUID)


                databaseReference.get().addOnSuccessListener {

                    val result = it.value as Map<*, *>

                    if (result["blockedby"] == myUUID) {
                        val childUpdates = mutableMapOf<String, Any?>()
                        childUpdates["/status/"] = FriendStatus.ACCEPTED.toString()
                        childUpdates["/blockedby/"] = null

                        databaseReference.updateChildren(childUpdates)

                        this@callbackFlow.trySendBlocking(Response.Success(true))
                    } else {
                        this@callbackFlow.trySendBlocking(Response.Success(false))
                    }
                }
            } catch (e: Exception) {
                this@callbackFlow.trySendBlocking(Response.Error(e.message ?: ERROR_MESSAGE))
            }

            awaitClose {
                channel.close()
                cancel()
            }
        }
}