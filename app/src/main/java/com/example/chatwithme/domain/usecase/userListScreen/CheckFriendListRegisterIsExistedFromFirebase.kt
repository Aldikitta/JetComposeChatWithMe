package com.example.chatwithme.domain.usecase.userListScreen

import com.example.chatwithme.domain.repository.UserListScreenRepository

class CheckFriendListRegisterIsExistedFromFirebase(
    private val userListScreenRepository: UserListScreenRepository
) {
    suspend operator fun invoke(
        acceptorEmail: String,
        acceptorUUID: String
    ) =
        userListScreenRepository.checkFriendListRegisterIsExistedFromFirebase(
            acceptorEmail,
            acceptorUUID
        )
}