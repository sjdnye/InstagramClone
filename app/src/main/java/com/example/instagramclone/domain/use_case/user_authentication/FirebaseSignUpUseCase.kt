package com.example.instagramclone.domain.use_case.user_authentication

import com.example.instagramclone.domain.repository.AuthenticationRepository
import javax.inject.Inject

class FirebaseSignUpUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(email: String, password: String, userName: String) =
        authenticationRepository.firebaseSignUp(email, password, userName)
}