package com.example.instagramclone.domain.use_case.user_authentication

data class AuthenticationUseCases(
    val isUserAuthenticatedUseCase: IsUserAuthenticatedUseCase,
    val firebaseSignInUseCase: FirebaseSignInUseCase,
    val firebaseSignOutUseCase: FirebaseSignOutUseCase,
    val firebaseSignUpUseCase: FirebaseSignUpUseCase,
    val getFirebaseAuthStateUseCase: GetFirebaseAuthStateUseCase,
)
