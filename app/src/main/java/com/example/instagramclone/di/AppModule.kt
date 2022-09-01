package com.example.instagramclone.di

import com.example.instagramclone.data.repository.AuthenticationRepositoryImpl
import com.example.instagramclone.domain.repository.AuthenticationRepository
import com.example.instagramclone.domain.use_case.user_authentication.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthentication(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirestoreStorage(): FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

    @Singleton
    @Provides
    fun provideAuthenticationRepository(firebaseAuth: FirebaseAuth,firestore: FirebaseFirestore) : AuthenticationRepository{
        return AuthenticationRepositoryImpl(firebaseAuth, firestore)
    }

    @Provides
    @Singleton
    fun provideAuthenticationUseCases(repository: AuthenticationRepository): AuthenticationUseCases {
        return AuthenticationUseCases(
            isUserAuthenticatedUseCase = IsUserAuthenticatedUseCase(repository),
            firebaseSignInUseCase = FirebaseSignInUseCase(repository),
            firebaseSignOutUseCase = FirebaseSignOutUseCase(repository),
            firebaseSignUpUseCase = FirebaseSignUpUseCase(repository),
            getFirebaseAuthStateUseCase = GetFirebaseAuthStateUseCase(repository)
        )
    }
}