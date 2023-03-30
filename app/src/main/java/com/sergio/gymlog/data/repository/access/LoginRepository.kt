package com.sergio.gymlog.data.repository.access

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.sergio.gymlog.data.model.FirebaseResource
import com.sergio.gymlog.data.service.authentication.FirebaseAuthenticationService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val firebaseAuthenticationService: FirebaseAuthenticationService
) {

    suspend fun loginWithEmailAndPassword(email: String,password: String): FirebaseResource<AuthResult> {
        return try {

            val result = firebaseAuthenticationService.loginWithEmailAndPassword(email, password).await()
            FirebaseResource.Success(result)

        }catch (e : Exception){

            FirebaseResource.Failure(e)

        }

    }

    suspend fun loginWithGoogleAccount(task : Task<GoogleSignInAccount>) : FirebaseResource<GoogleSignInAccount>{

        return if(task.isSuccessful) {

            firebaseAuthenticationService.loginWithGoogleAccount(task)
            FirebaseResource.Success(task.result)

        } else {

            FirebaseResource.Failure(task.exception!!)

        }

    }

    fun logout() {

        firebaseAuthenticationService.logout()

    }

}