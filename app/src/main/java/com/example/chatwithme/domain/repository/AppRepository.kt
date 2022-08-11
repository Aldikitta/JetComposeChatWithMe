package com.example.chatwithme.domain.repository

import com.example.chatwithme.utils.Response
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    //Login Screen
    fun isUserAuthenticatedInFirebase(): Flow<Response<Boolean>>
    suspend fun signIn(email: String, password: String): Flow<Response<Boolean>>
    suspend fun signUp(email: String, password: String): Flow<Response<Boolean>>
}