package com.example.chatwithme.domain.usecase.profileScreen

import com.example.chatwithme.domain.repository.ProfileScreenRepository

class LoadProfileFromFirebase(
    private val profileScreenRepository: ProfileScreenRepository
) {
    suspend operator fun invoke() = profileScreenRepository.loadProfileFromFirebase()
}