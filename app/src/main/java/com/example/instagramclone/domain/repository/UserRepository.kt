package com.example.instagramclone.domain.repository

import com.example.instagramclone.domain.model.User
import com.example.instagramclone.utils.Response
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserDetails(userId: String): Flow<Response<User>>

    fun setUserDetails(
        userId: String,
        name: String,
        userName: String,
        bio: String,
        websiteUrl: String
    ): Flow<Response<Boolean>>

}