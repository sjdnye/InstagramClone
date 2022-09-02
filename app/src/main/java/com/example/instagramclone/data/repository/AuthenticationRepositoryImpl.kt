package com.example.instagramclone.data.repository

import com.example.instagramclone.domain.model.User
import com.example.instagramclone.domain.repository.AuthenticationRepository
import com.example.instagramclone.utils.Constants.COLLECTION_NAME_USERS
import com.example.instagramclone.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthenticationRepository {

    private var operationSuccessful: Boolean = false

    override fun isUserAuthenticatedInFirebase(): Boolean {
        return auth.currentUser != null
    }

    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun firebaseSignIn(email: String, password: String): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            emit(Response.Loading)
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSuccessful = true
            }.await()
            emit(Response.Success(operationSuccessful))

        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An Unexpected Error!"))
        }
    }

    override fun firebaseSignOut(): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            auth.signOut()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An Unexpected Error!"))
        }
    }

    override fun firebaseSignUp(
        email: String,
        password: String,
        userName: String
    ): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            emit(Response.Loading)
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSuccessful = true
            }.await()

            if (operationSuccessful) {
                val userId = auth.currentUser?.uid!!
                val user =
                    User(userName = userName, email = email, password = password, userId = userId)
                firestore.collection(COLLECTION_NAME_USERS).document(userId).set(user)
                    .addOnSuccessListener {

                    }.await()
                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Success(operationSuccessful))

            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An Unexpected Error!"))
        }
    }
}