package com.example.chatwithme.data.repository

import com.example.chatwithme.domain.model.FriendListRegister
import com.example.chatwithme.domain.model.FriendListRow
import com.example.chatwithme.domain.model.User
import com.example.chatwithme.domain.repository.UserListScreenRepository
import com.example.chatwithme.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserListScreenRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase,
    private val storage: FirebaseStorage
) : UserListScreenRepository {
    override suspend fun loadAcceptedFriendRequestListFromFirebase(): Flow<Response<List<FriendListRow>>> {
        TODO("Not yet implemented")
    }

    override suspend fun loadPendingFriendRequestListFromFirebase(): Flow<Response<List<FriendListRegister>>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchUserFromFirebase(userEmail: String): Flow<Response<User?>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkChatRoomExistedFromFirebase(acceptorUUID: String): Flow<Response<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun createChatRoomToFirebase(acceptorUUID: String): Flow<Response<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkFriendListRegisterIsExistedFromFirebase(
        acceptorEmail: String,
        acceptorUUID: String
    ): Flow<Response<FriendListRegister>> {
        TODO("Not yet implemented")
    }

    override suspend fun createFriendListRegisterToFirebase(
        chatRoomUUID: String,
        acceptorEmail: String,
        acceptorUUID: String,
        acceptorOneSignalUserId: String
    ): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun acceptPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun rejectPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun openBlockedFriendToFirebase(registerUUID: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }
}