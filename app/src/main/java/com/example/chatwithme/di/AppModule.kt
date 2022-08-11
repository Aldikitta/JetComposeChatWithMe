package com.example.chatwithme.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.chatwithme.data.repository.AppRepositoryImpl
import com.example.chatwithme.domain.repository.AuthScreenRepository
import com.example.chatwithme.domain.usecase.UseCases
import com.example.chatwithme.domain.usecase.authScreen.IsUserAuthenticatedInFirebase
import com.example.chatwithme.domain.usecase.authScreen.SignIn
import com.example.chatwithme.domain.usecase.authScreen.SignUp
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
        storage: FirebaseStorage,
        database: FirebaseDatabase
    ): AuthScreenRepository = AppRepositoryImpl(auth, storage, database)

    @Provides
    fun provideAuthScreenUsecase(authRepository: AuthScreenRepository) = UseCases(
        isUserAuthenticated = IsUserAuthenticatedInFirebase(authRepository),
        signIn = SignIn(authRepository),
        signUp = SignUp(authRepository)
    )

}