package com.example.chatwithme.data.repository

import androidx.compose.runtime.saveable.autoSaver
import com.example.chatwithme.core.Constants.ERROR_MESSAGE
import com.example.chatwithme.domain.model.ChatMessage
import com.example.chatwithme.domain.model.MessageStatus
import com.example.chatwithme.domain.model.User
import com.example.chatwithme.domain.repository.ChatScreenRepository
import com.example.chatwithme.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.onesignal.OneSignal
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatScreenRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : ChatScreenRepository {
    override suspend fun insertMessageToFirebase(
        chatRoomUUID: String,
        messageContent: String,
        registerUUID: String,
        oneSignalUserId: String
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            val userUUID = auth.currentUser?.uid
            val userEmail = auth.currentUser?.email
            val messageUUID = UUID.randomUUID().toString()
            OneSignal.postNotification(
                JSONObject("{'contents': {'en':'$userEmail: $messageContent'}, 'include_player_ids': ['$oneSignalUserId']}"),
                object : OneSignal.PostNotificationResponseHandler {
                    override fun onSuccess(p0: JSONObject?) {
                        println("onSuccess")
                    }

                    override fun onFailure(p0: JSONObject?) {
                        println("onFailure: " + p0.toString())
                    }
                })

            val message = ChatMessage(
                userUUID!!,
                messageContent,
                System.currentTimeMillis(),
                MessageStatus.RECEIVED.toString()
            )

            val databaseRefForLastMessage =
                database.reference.child("Friend_List").child(registerUUID)
                    .child("lastMessage")
            databaseRefForLastMessage.setValue(message).await() // for last message

            val databaseRefForChatMessage =
                database.reference.child("Chat_Rooms").child(chatRoomUUID)
                    .child(messageUUID)
            databaseRefForChatMessage.setValue(message).await() // for insert message

            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun loadMessagesFromFirebase(
        chatRoomUUID: String,
        opponentUUID: String,
        registerUUID: String
    ): Flow<Response<List<ChatMessage>>> = callbackFlow {
        try {
            this@callbackFlow.trySendBlocking(Response.Loading)
            val userUUID = auth.currentUser?.uid

            val databaseRefForMessageStatus =
                database.getReference("Friend_List").child(registerUUID)
                    .child("lastMessage")
            val lastMessageProfileUUID =
                databaseRefForMessageStatus.child("profileUUID").get().await().value as String

            if (lastMessageProfileUUID != userUUID) {
                databaseRefForMessageStatus.child("status").setValue(MessageStatus.READ.toString())
            }
            val databaseRefForLoadMessages =
                database.getReference("Chat_Rooms").child(chatRoomUUID)

            val postListener =
                databaseRefForLoadMessages.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val messageList = arrayListOf<ChatMessage>()
                        var unReadMessageKeys = listOf<String>()

                        val job2 = launch {
                            snapshot.children.forEach {
                                if (it.value?.javaClass != Boolean::class.java) {
                                    val chatMessage = it.getValue(ChatMessage::class.java)
                                    if (chatMessage != null) {
                                        messageList.add(chatMessage)

                                        if (chatMessage.profileUUID != userUUID && chatMessage.status == MessageStatus.RECEIVED.toString()) {
                                            unReadMessageKeys =
                                                unReadMessageKeys + it.key.toString()
                                        }
                                    }
                                }
                            }
                            messageList.sortBy { it.date }
                            this@callbackFlow.trySendBlocking(Response.Success(messageList))
                        }
                        job2.invokeOnCompletion {
                            for (i in unReadMessageKeys) {
                                databaseRefForLoadMessages.child(i).updateChildren(
                                    mapOf(
                                        Pair(
                                            "/status/",
                                            MessageStatus.READ
                                        )
                                    )
                                )
                            }
                        }
                        messageList.clear()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        this@callbackFlow.trySendBlocking(Response.Error(error.message))
                    }
                })
            databaseRefForLoadMessages.addValueEventListener(postListener)

            awaitClose {
                databaseRefForLoadMessages.removeEventListener(postListener)
                channel.close()
                cancel()
            }
        } catch (e: Exception) {
            this@callbackFlow.trySendBlocking(Response.Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun loadOpponentProfileFromFirebase(opponentUUID: String): Flow<Response<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun blockFriendToFirebase(registerUUID: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }
}