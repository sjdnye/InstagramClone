package com.example.instagramclone.data.repository

import com.example.instagramclone.domain.model.User
import com.example.instagramclone.domain.repository.UserRepository
import com.example.instagramclone.utils.Constants.COLLECTION_NAME_USERS
import com.example.instagramclone.utils.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore:FirebaseFirestore
) : UserRepository{
    private var operationSuccessful = false
    override fun getUserDetails(userId: String): Flow<Response<User>> = callbackFlow {
        Response.Loading
        val snapshotListener = firestore.collection(COLLECTION_NAME_USERS)
            .document(userId)
            .addSnapshotListener{snapshot, error ->
                val response = if (snapshot != null){
                    val userInfo = snapshot.toObject(User::class.java)
                    Response.Success<User>(userInfo!!)
                }else{
                    Response.Error(error?.message?: error.toString())
                }
                trySend(response).isSuccess

            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun setUserDetails(
        userId: String,
        name: String,
        userName: String,
        bio: String,
        websiteUrl: String
    ) : Flow<Response<Boolean>> = flow{
        try {
            operationSuccessful = false
            val userObj = mutableMapOf<String,String>()
            userObj["name"] = name
            userObj["userName"] = userName
            userObj["bio"] = bio
            userObj["webSiteUrl"] = websiteUrl
            firestore.collection(COLLECTION_NAME_USERS).document(userId).update(userObj as Map<String, Any>)
                .addOnSuccessListener {
                    operationSuccessful = true
                }.await()
            if (operationSuccessful){
                emit(Response.Success(operationSuccessful))
            }
            else{
                emit(Response.Error("Something went wrong! Please try again."))
            }



        }catch (e:Exception){
            Response.Error(e.localizedMessage?:"An Unexpected Error!")
        }
    }
}