package com.example.chatwithme.domain.usecase.profileScreen

import com.example.chatwithme.domain.model.User
import com.example.chatwithme.domain.repository.ProfileScreenRepository

class CreateOrUpdateProfileToFirebase(
    private val profileScreenRepository: ProfileScreenRepository
) {
    suspend operator fun invoke(user: User) =
        profileScreenRepository.createOrUpdateProfileToFirebase(user)
}