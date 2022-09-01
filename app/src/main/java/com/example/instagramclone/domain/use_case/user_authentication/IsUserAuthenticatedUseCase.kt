package com.example.instagramclone.domain.use_case.user_authentication

import com.example.instagramclone.domain.repository.AuthenticationRepository
import javax.inject.Inject

class IsUserAuthenticatedUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke() = authenticationRepository.isUserAuthenticatedInFirebase()
}