package com.example.instagramclone.domain.use_case.user_profile

import com.example.instagramclone.domain.repository.UserRepository
import com.example.instagramclone.utils.Response
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId:String) = repository.getUserDetails(userId)
}