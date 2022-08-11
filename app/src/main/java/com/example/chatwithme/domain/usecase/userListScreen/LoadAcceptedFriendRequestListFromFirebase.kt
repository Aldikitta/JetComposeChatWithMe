package com.example.chatwithme.domain.usecase.userListScreen

import com.example.chatwithme.domain.repository.UserListScreenRepository

class LoadAcceptedFriendRequestListFromFirebase(
    private val userListScreenRepository: UserListScreenRepository
) {
    suspend operator fun invoke() =
        userListScreenRepository.loadAcceptedFriendRequestListFromFirebase()
}