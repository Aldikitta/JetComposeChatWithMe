//package com.example.chatwithme.domain.repository
//
//import android.net.Uri
//import com.example.chatwithme.domain.model.*
//import com.example.chatwithme.utils.Response
//import kotlinx.coroutines.flow.Flow
//
//interface AppRepository {
//
//    //Login Screen
//    fun isUserAuthenticatedInFirebase(): Flow<Response<Boolean>>
//    suspend fun signIn(email: String, password: String): Flow<Response<Boolean>>
//    suspend fun signUp(email: String, password: String): Flow<Response<Boolean>>
//
//    //Profile Screen
//    suspend fun signOut(): Flow<Response<Boolean>>
//    suspend fun uploadPictureToFirebase(url: Uri): Flow<Response<String>>
//    suspend fun createOrUpdateProfileToFirebase(user: User): Flow<Response<Boolean>>
//    suspend fun loadProfileFromFirebase(): Flow<Response<User>>
//    suspend fun setUserStatusToFirebase(userStatus: UserStatus): Flow<Response<Boolean>>
//
//    //UserListScreen
//    suspend fun loadAcceptedFriendRequestListFromFirebase(): Flow<Response<List<FriendListRow>>>
//    suspend fun loadPendingFriendRequestListFromFirebase(): Flow<Response<List<FriendListRegister>>>
//
//    suspend fun searchUserFromFirebase(userEmail: String): Flow<Response<User?>>
//
//    suspend fun checkChatRoomExistedFromFirebase(acceptorUUID: String): Flow<Response<String>>
//    suspend fun createChatRoomToFirebase(acceptorUUID: String): Flow<Response<String>>
//
//    suspend fun checkFriendListRegisterIsExistedFromFirebase(
//        acceptorEmail: String,
//        acceptorUUID: String
//    ): Flow<Response<FriendListRegister>>
//
//    suspend fun createFriendListRegisterToFirebase(
//        chatRoomUUID: String,
//        acceptorEmail: String,
//        acceptorUUID: String,
//        acceptorOneSignalUserId: String
//    ): Flow<Response<Boolean>>
//
//    suspend fun acceptPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>>
//    suspend fun rejectPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>>
//    suspend fun openBlockedFriendToFirebase(registerUUID: String): Flow<Response<Boolean>>
//
//    //ChatScreen
//    suspend fun insertMessageToFirebase(
//        chatRoomUUID: String,
//        messageContent: String,
//        registerUUID: String,
//        oneSignalUserId: String
//    ): Flow<Response<Boolean>>
//
//    suspend fun loadMessagesFromFirebase(
//        chatRoomUUID: String,
//        opponentUUID: String,
//        registerUUID: String
//    ): Flow<Response<List<ChatMessage>>>
//
//    suspend fun loadOpponentProfileFromFirebase(opponentUUID: String): Flow<Response<User>>
//    suspend fun blockFriendToFirebase(registerUUID: String): Flow<Response<Boolean>>
//}