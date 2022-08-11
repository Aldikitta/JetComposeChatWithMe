package com.example.chatwithme.data.repository

import androidx.compose.runtime.saveable.autoSaver
import com.example.chatwithme.core.Constants.ERROR_MESSAGE
import com.example.chatwithme.domain.model.ChatMessage
import com.example.chatwithme.domain.model.MessageStatus
import com.example.chatwithme.domain.model.User
import com.example.chatwithme.domain.repository.ChatScreenRepository
import com.example.chatwithme.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.onesignal.OneSignal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    ): Flow<Response<List<ChatMessage>>> {
        TODO("Not yet implemented")
    }

    override suspend fun loadOpponentProfileFromFirebase(opponentUUID: String): Flow<Response<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun blockFriendToFirebase(registerUUID: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }
}