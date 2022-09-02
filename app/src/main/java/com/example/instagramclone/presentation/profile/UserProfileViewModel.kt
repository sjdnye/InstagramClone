package com.example.instagramclone.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramclone.domain.model.User
import com.example.instagramclone.domain.use_case.user_profile.UserProfileUseCases
import com.example.instagramclone.utils.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userProfileUseCases: UserProfileUseCases
) : ViewModel() {
    private val userId = auth.currentUser?.uid

    private val _getUserData = mutableStateOf<Response<User?>>(Response.Success(null))
    val getUserData: State<Response<User?>> = _getUserData

    private val _setUserData = mutableStateOf<Response<Boolean?>>(Response.Success(null))
    val setUserData: State<Response<Boolean?>> = _setUserData

    init {
        getUserInfo()
    }

    fun getUserInfo(){
        userId?.let{
            viewModelScope.launch {
                userProfileUseCases.getUserDetailsUseCase(userId = it).collect{ response ->
                    _getUserData.value = response
                }
            }
        }
    }

    fun setUserDetails(
        name: String,
        userName: String,
        bio: String,
        websiteUrl: String
    ){
       userId?.let{
           viewModelScope.launch {
               userProfileUseCases.setUserDetailsUseCase(
                   userId = userId,
                   name = name,
                   userName = userName,
                   bio = bio,
                   websiteUrl = websiteUrl
               ).collect{
                   _setUserData.value = it
               }
           }
       }
    }
}