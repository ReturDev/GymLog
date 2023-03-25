package com.sergio.gymlog.data.authentication

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.sergio.gymlog.User
import com.sergio.gymlog.data.model.FirebaseResource

interface FirebaseAuthentication {

    fun checkUserLogged() : Boolean
    fun getUserData() : User?
    suspend fun  loginWithEmailAndPassword(email : String, password : String)  : FirebaseResource<AuthResult>
    suspend fun  signUpWithEmailAndPassword(email : String, password : String) : FirebaseResource<AuthResult>
    suspend fun loginWithGoogleAccount(task : Task<GoogleSignInAccount>) : FirebaseResource<GoogleSignInAccount>
    fun logout()

}