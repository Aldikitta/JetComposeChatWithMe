package com.example.chatwithme.domain.repository

import android.net.Uri
import com.example.chatwithme.domain.model.FriendListRegister
import com.example.chatwithme.domain.model.FriendListRow
import com.example.chatwithme.domain.model.User
import com.example.chatwithme.domain.model.UserStatus
import com.example.chatwithme.utils.Response
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    //Login Screen
    fun isUserAuthenticatedInFirebase(): Flow<Response<Boolean>>
    suspend fun signIn(email: String, password: String): Flow<Response<Boolean>>
    suspend fun signUp(email: String, password: String): Flow<Response<Boolean>>

    //Profile Screen
    suspend fun signOut(): Flow<Response<Boolean>>
    suspend fun uploadPictureToFirebase(url: Uri): Flow<Response<String>>
    suspend fun createOrUpdateProfileToFirebase(user: User): Flow<Response<Boolean>>
    suspend fun loadProfileFromFirebase(): Flow<Response<User>>
    suspend fun setUserStatusToFirebase(userStatus: UserStatus): Flow<Response<Boolean>>

    //UserListScreen
    suspend fun loadAcceptedFriendRequestListFromFirebase(): Flow<Response<List<FriendListRow>>>
    suspend fun loadPendingFriendRequestListFromFirebase(): Flow<Response<List<FriendListRegister>>>

    suspend fun searchUserFromFirebase(userEmail: String): Flow<Response<User?>>

    suspend fun checkChatRoomExistedFromFirebase(acceptorUUID: String): Flow<Response<String>>
    suspend fun createChatRoomToFirebase(acceptorUUID: String): Flow<Response<String>>

    suspend fun checkFriendListRegisterIsExistedFromFirebase(
        acceptorEmail: String,
        acceptorUUID: String
    ): Flow<Response<FriendListRegister>>

    suspend fun createFriendListRegisterToFirebase(
        chatRoomUUID: String,
        acceptorEmail: String,
        acceptorUUID: String,
        acceptorOneSignalUserId: String
    ): Flow<Response<Boolean>>

    suspend fun acceptPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>>
    suspend fun rejectPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>>
    suspend fun openBlockedFriendToFirebase(registerUUID: String): Flow<Response<Boolean>>
}