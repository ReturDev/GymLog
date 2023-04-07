package com.sergio.gymlog.data.repository.access

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.sergio.gymlog.data.model.remote.FirebaseResource
import com.sergio.gymlog.data.remote.authentication.FirebaseAuthenticationService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val firebaseAuthenticationService: FirebaseAuthenticationService
) {

    suspend fun signUpWithEmailAndPassword(email: String,password: String) : FirebaseResource<AuthResult> {
        return try {

            val result = firebaseAuthenticationService.signUpWithEmailAndPassword(email, password).await()
            FirebaseResource.Success(result)

        }catch (e : Exception){

            FirebaseResource.Failure(e)

        }
    }

    suspend fun signUpWithGoogleAccount(task : Task<GoogleSignInAccount>) : FirebaseResource<GoogleSignInAccount> {

        return if(task.isSuccessful) {

            firebaseAuthenticationService.loginWithGoogleAccount(task)
            FirebaseResource.Success(task.result)

        } else {

            FirebaseResource.Failure(task.exception!!)

        }

    }

}