package com.example.chatwithme.domain.repository

import android.net.Uri
import com.example.chatwithme.domain.model.User
import com.example.chatwithme.domain.model.UserStatus
import com.example.chatwithme.utils.Response
import kotlinx.coroutines.flow.Flow

interface ProfileScreenRepository {
    suspend fun signOut(): Flow<Response<Boolean>>
    suspend fun uploadPictureToFirebase(url: Uri): Flow<Response<String>>
    suspend fun createOrUpdateProfileToFirebase(user: User): Flow<Response<Boolean>>
    suspend fun loadProfileFromFirebase(): Flow<Response<User>>
    suspend fun setUserStatusToFirebase(userStatus: UserStatus): Flow<Response<Boolean>>
}