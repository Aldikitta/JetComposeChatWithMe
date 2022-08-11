package com.example.chatwithme.domain.usecase.userListScreen

import com.example.chatwithme.domain.repository.UserListScreenRepository

class SearchUserFromFirebase(
    private val userListScreenRepository: UserListScreenRepository
) {
    suspend operator fun invoke(userEmail: String) =
        userListScreenRepository.searchUserFromFirebase(userEmail)
}