package com.example.instagramclone.presentation.authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramclone.domain.use_case.user_authentication.AuthenticationUseCases
import com.example.instagramclone.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationUseCases: AuthenticationUseCases
) : ViewModel(){

    val isUserAuthenticated get() = authenticationUseCases.isUserAuthenticatedUseCase()

    private var _signInState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signInState : State<Response<Boolean>> = _signInState

    private var _signUpState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signUpState : State<Response<Boolean>> = _signUpState

    private var _signOutState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signOutState : State<Response<Boolean>> = _signOutState

    private var _firebaseAuthState = mutableStateOf<Boolean>(false)
    val firebaseAuthState : State<Boolean> = _firebaseAuthState

    fun signIn(email:String, password:String){
        viewModelScope.launch {
            authenticationUseCases.firebaseSignInUseCase(email = email, password = password).collect{
                _signInState.value = it
            }
        }
    }

    fun signUp(email: String,password: String,userName:String){
        viewModelScope.launch {
            authenticationUseCases.firebaseSignUpUseCase(email = email, password = password, userName = userName).collect{
                _signUpState.value = it
            }
        }
    }

    fun signOut(){
        viewModelScope.launch {
            authenticationUseCases.firebaseSignOutUseCase().collect{
                _signOutState.value = it
            }
        }
    }

    fun getFirebaseAuthState(){
        viewModelScope.launch {
            authenticationUseCases.getFirebaseAuthStateUseCase().collect{
                _firebaseAuthState.value = it
            }
        }
    }
}