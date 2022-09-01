package com.example.instagramclone.domain.use_case.user_authentication

import com.example.instagramclone.domain.repository.AuthenticationRepository
import javax.inject.Inject

class FirebaseSignInUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(email:String, password:String) = authenticationRepository.firebaseSignIn(email, password)
}