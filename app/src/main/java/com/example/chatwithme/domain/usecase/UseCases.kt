package com.example.chatwithme.domain.usecase

import com.example.chatwithme.domain.usecase.authScreen.IsUserAuthenticatedInFirebase
import com.example.chatwithme.domain.usecase.authScreen.SignIn
import com.example.chatwithme.domain.usecase.authScreen.SignUp

data class UseCases(
    val isUserAuthenticated: IsUserAuthenticatedInFirebase,
    val signIn: SignIn,
    val signUp: SignUp,
)