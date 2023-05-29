package com.sergio.gymlog.data.remote.authentication

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface FirebaseAuthentication {
    suspend fun  loginWithEmailAndPassword(email : String, password : String)  : Task<AuthResult>
    suspend fun  signUpWithEmailAndPassword(email : String, password : String) : Task<AuthResult>
    suspend fun loginWithGoogleAccount(task : Task<GoogleSignInAccount>)
    fun logout()

    fun sendPasswordRecoveryEmail(email: String): Task<Void>
}