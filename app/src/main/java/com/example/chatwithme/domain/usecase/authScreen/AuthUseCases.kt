package com.example.chatwithme.domain.usecase.authScreen

data class AuthUseCases(
    val isUserAuthenticated: IsUserAuthenticatedInFirebase,
    val signIn: SignIn,
    val signUp: SignUp,
)