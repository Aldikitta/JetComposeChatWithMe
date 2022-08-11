package com.example.chatwithme.domain.usecase.authScreen

import com.example.chatwithme.domain.usecase.authScreen.IsUserAuthenticatedInFirebase
import com.example.chatwithme.domain.usecase.authScreen.SignIn
import com.example.chatwithme.domain.usecase.authScreen.SignUp

data class AuthUseCases(
    val isUserAuthenticated: IsUserAuthenticatedInFirebase,
    val signIn: SignIn,
    val signUp: SignUp,
)