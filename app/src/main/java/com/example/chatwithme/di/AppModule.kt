package com.example.chatwithme.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.chatwithme.data.repository.AuthScreenRepositoryImpl
import com.example.chatwithme.data.repository.ChatScreenRepositoryImpl
import com.example.chatwithme.data.repository.ProfileScreenRepositoryImpl
import com.example.chatwithme.data.repository.UserListScreenRepositoryImpl
import com.example.chatwithme.domain.repository.AuthScreenRepository
import com.example.chatwithme.domain.repository.ChatScreenRepository
import com.example.chatwithme.domain.repository.ProfileScreenRepository
import com.example.chatwithme.domain.repository.UserListScreenRepository
import com.example.chatwithme.domain.usecase.authScreen.AuthUseCases
import com.example.chatwithme.domain.usecase.authScreen.IsUserAuthenticatedInFirebase
import com.example.chatwithme.domain.usecase.authScreen.SignIn
import com.example.chatwithme.domain.usecase.authScreen.SignUp
import com.example.chatwithme.domain.usecase.chatScreen.*
import com.example.chatwithme.domain.usecase.profileScreen.*
import com.example.chatwithme.domain.usecase.userListScreen.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("login")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseAuthInstance() = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseStorageInstance() = FirebaseStorage.getInstance()

    @Provides
    fun provideFirebaseDatabaseInstance() = FirebaseDatabase.getInstance()

//    @Provides
//    fun provideSharedPreferences(application: Application) =
//        application.getSharedPreferences("login", Context.MODE_PRIVATE)

    @Provides
    fun providesDataStore(application: Application) = application.dataStore

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
    ): AuthScreenRepository = AuthScreenRepositoryImpl(auth)

    @Provides
    fun provideChatScreenRepository(
        auth: FirebaseAuth,
        database: FirebaseDatabase
    ): ChatScreenRepository = ChatScreenRepositoryImpl(auth, database)

    @Provides
    fun provideProfileScreenRepository(
        auth: FirebaseAuth,
        database: FirebaseDatabase,
        storage: FirebaseStorage
    ): ProfileScreenRepository = ProfileScreenRepositoryImpl(auth, database, storage)

    @Provides
    fun provideUserListScreenRepository(
        auth: FirebaseAuth,
        database: FirebaseDatabase
    ): UserListScreenRepository = UserListScreenRepositoryImpl(auth, database)

    @Provides
    fun provideAuthScreenUseCase(authRepository: AuthScreenRepository) = AuthUseCases(
        isUserAuthenticated = IsUserAuthenticatedInFirebase(authRepository),
        signIn = SignIn(authRepository),
        signUp = SignUp(authRepository)
    )

    @Provides
    fun provideChatScreenUseCase(chatScreenRepository: ChatScreenRepository) = ChatScreenUseCases(
        blockFriendToFirebase = BlockFriendToFirebase(chatScreenRepository),
        insertMessageToFirebase = InsertMessageToFirebase(chatScreenRepository),
        loadMessageFromFirebase = LoadMessageFromFirebase(chatScreenRepository),
        opponentProfileFromFirebase = LoadOpponentProfileFromFirebase(chatScreenRepository)
    )

    @Provides
    fun provideProfileScreenUseCase(profileScreenRepository: ProfileScreenRepository) =
        ProfileScreenUseCases(
            createOrUpdateProfileToFirebase = CreateOrUpdateProfileToFirebase(
                profileScreenRepository
            ),
            loadProfileFromFirebase = LoadProfileFromFirebase(profileScreenRepository),
            setUserStatusToFirebase = SetUserStatusToFirebase(profileScreenRepository),
            signOut = SignOut(profileScreenRepository),
            uploadPictureToFirebase = UploadPictureToFirebase(profileScreenRepository)
        )

    @Provides
    fun provideUserListScreenUseCase(userListScreenRepository: UserListScreenRepository) =
        UserListScreenUseCases(
            acceptPendingFriendRequestToFirebase = AcceptPendingFriendRequestToFirebase(
                userListScreenRepository
            ),
            checkChatRoomExistedFromFirebase = CheckChatRoomExistedFromFirebase(
                userListScreenRepository
            ),
            checkFriendListRegisterIsExistedFromFirebase = CheckFriendListRegisterIsExistedFromFirebase(
                userListScreenRepository
            ),
            createChatRoomToFirebase = CreateChatRoomToFirebase(userListScreenRepository),
            createFriendListRegisterToFirebase = CreateFriendListRegisterToFirebase(
                userListScreenRepository
            ),
            loadAcceptedFriendRequestListFromFirebase = LoadAcceptedFriendRequestListFromFirebase(
                userListScreenRepository
            ),
            loadPendingFriendRequestListFromFirebase = LoadPendingFriendRequestListFromFirebase(
                userListScreenRepository
            ),
            openBlockedFriendToFirebase = OpenBlockedFriendToFirebase(userListScreenRepository),
            rejectPendingFriendRequestToFirebase = RejectPendingFriendRequestToFirebase(
                userListScreenRepository
            ),
            searchUserFromFirebase = SearchUserFromFirebase(userListScreenRepository),
        )
}