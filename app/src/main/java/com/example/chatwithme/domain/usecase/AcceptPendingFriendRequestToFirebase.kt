package com.example.chatwithme.domain.usecase

import com.example.chatwithme.domain.repository.UserListScreenRepository

class AcceptPendingFriendRequestToFirebase(
    private val userListScreenRepository: UserListScreenRepository
) {
    suspend operator fun invoke(registerUUID: String) =
        userListScreenRepository.acceptPendingFriendRequestToFirebase(registerUUID)
}