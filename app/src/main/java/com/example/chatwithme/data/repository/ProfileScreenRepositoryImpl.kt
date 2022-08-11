package com.example.chatwithme.data.repository

import android.net.Uri
import com.example.chatwithme.core.Constants.ERROR_MESSAGE
import com.example.chatwithme.domain.model.User
import com.example.chatwithme.domain.model.UserStatus
import com.example.chatwithme.domain.repository.ProfileScreenRepository
import com.example.chatwithme.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileScreenRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase,
    private val storage: FirebaseStorage
) : ProfileScreenRepository {
    override suspend fun signOut(): Flow<Response<Boolean>> = callbackFlow {
        try {
            this@callbackFlow.trySendBlocking(Response.Loading)
            auth.signOut().apply {
                this@callbackFlow.trySendBlocking(Response.Success(true))
            }
        } catch (e: Exception) {
            this@callbackFlow.trySendBlocking(Response.Error(e.message ?: ERROR_MESSAGE))
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }

    override suspend fun uploadPictureToFirebase(url: Uri): Flow<Response<String>> = flow {
        try {
            emit(Response.Loading)
            val uuidImage = UUID.randomUUID()
            val imageName = "images/$uuidImage.jpg"
            val storageRef = storage.reference.child(imageName)

            storageRef.putFile(url).apply {}.await()
            var downloadUrl = ""
            storageRef.downloadUrl.addOnSuccessListener {
                downloadUrl = it.toString()
            }.await()
            emit(Response.Success(downloadUrl))


        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun createOrUpdateProfileToFirebase(user: User): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun loadProfileFromFirebase(): Flow<Response<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun setUserStatusToFirebase(userStatus: UserStatus): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

}