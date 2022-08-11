package com.example.chatwithme.domain.usecase.profileScreen

import com.example.chatwithme.domain.model.UserStatus
import com.example.chatwithme.domain.repository.ProfileScreenRepository

class SetUserStatusToFirebase(
    private val profileScreenRepository: ProfileScreenRepository
) {
    suspend operator fun invoke(userStatus: UserStatus) =
        profileScreenRepository.setUserStatusToFirebase(userStatus)
}