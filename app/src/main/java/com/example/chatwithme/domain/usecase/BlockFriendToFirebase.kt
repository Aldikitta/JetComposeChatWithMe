package com.example.chatwithme.domain.usecase

import com.example.chatwithme.domain.repository.ChatScreenRepository

class BlockFriendToFirebase(
    private val chatScreenRepository: ChatScreenRepository
) {
    suspend operator fun invoke(registerUUID: String) =
        chatScreenRepository.blockFriendToFirebase(registerUUID)
}