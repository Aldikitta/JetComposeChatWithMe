package com.example.chatwithme.domain.usecase.chatScreen

import com.example.chatwithme.domain.repository.ChatScreenRepository

class LoadOpponentProfileFromFirebase(
    private val chatScreenRepository: ChatScreenRepository
) {
    suspend operator fun invoke(opponentUUID: String) =
        chatScreenRepository.loadOpponentProfileFromFirebase(opponentUUID)
}