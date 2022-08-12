package com.example.chatwithme.data.repository

import com.example.chatwithme.core.Constants.ERROR_MESSAGE
import com.example.chatwithme.domain.model.*
import com.example.chatwithme.domain.repository.UserListScreenRepository
import com.example.chatwithme.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserListScreenRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase,
    private val storage: FirebaseStorage
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

    override suspend fun loadPendingFriendRequestListFromFirebase(): Flow<Response<List<FriendListRegister>>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchUserFromFirebase(userEmail: String): Flow<Response<User?>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkChatRoomExistedFromFirebase(acceptorUUID: String): Flow<Response<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun createChatRoomToFirebase(acceptorUUID: String): Flow<Response<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkFriendListRegisterIsExistedFromFirebase(
        acceptorEmail: String,
        acceptorUUID: String
    ): Flow<Response<FriendListRegister>> {
        TODO("Not yet implemented")
    }

    override suspend fun createFriendListRegisterToFirebase(
        chatRoomUUID: String,
        acceptorEmail: String,
        acceptorUUID: String,
        acceptorOneSignalUserId: String
    ): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun acceptPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun rejectPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun openBlockedFriendToFirebase(registerUUID: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }
}