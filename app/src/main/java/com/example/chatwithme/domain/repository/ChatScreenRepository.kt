package com.example.chatwithme.domain.repository

import com.example.chatwithme.domain.model.ChatMessage
import com.example.chatwithme.domain.model.User
import com.example.chatwithme.utils.Response
import kotlinx.coroutines.flow.Flow

interface ChatScreenRepository {
    suspend fun insertMessageToFirebase(
        chatRoomUUID: String,
        messageContent: String,
        registerUUID: String,
        oneSignalUserId: String
    ): Flow<Response<Boolean>>

    suspend fun loadMessagesFromFirebase(
        chatRoomUUID: String,
        opponentUUID: String,
        registerUUID: String
    ): Flow<Response<List<ChatMessage>>>

    suspend fun loadOpponentProfileFromFirebase(opponentUUID: String): Flow<Response<User>>
    suspend fun blockFriendToFirebase(registerUUID: String): Flow<Response<Boolean>>
}