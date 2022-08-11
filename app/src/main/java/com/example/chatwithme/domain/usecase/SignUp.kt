package com.example.chatwithme.domain.usecase

import com.example.chatwithme.domain.repository.AuthScreenRepository

class SignUp(
    private val authScreenRepository: AuthScreenRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        authScreenRepository.signUp(email, password)
}