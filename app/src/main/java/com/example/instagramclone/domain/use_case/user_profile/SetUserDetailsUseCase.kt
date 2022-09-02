package com.example.instagramclone.domain.use_case.user_profile

import com.example.instagramclone.domain.repository.UserRepository
import javax.inject.Inject

class SetUserDetailsUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(
        userId: String,
        name: String,
        userName: String,
        bio: String,
        websiteUrl: String
    ) = repository.setUserDetails(
        userId = userId,
        name = name,
        userName = userName,
        bio = bio,
        websiteUrl = websiteUrl
    )
}