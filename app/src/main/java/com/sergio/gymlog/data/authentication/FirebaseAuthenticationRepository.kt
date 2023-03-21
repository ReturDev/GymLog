package com.sergio.gymlog.data.authentication


import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sergio.gymlog.User
import com.sergio.gymlog.data.model.FirebaseAuthResource
import com.sergio.gymlog.data.model.Provider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirebaseAuthenticationRepository @Inject constructor(
    private  val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    @ApplicationContext private val context: Context
    ) : FirebaseAuthenticationService {


    override fun checkUserLogged(): Boolean {

        return firebaseAuth.currentUser != null

    }

    override fun getUserData(): User? {


        var user : User? = null

        firebaseAuth.currentUser?.let {
            it.providerData.forEach { y ->
                Log.e("Provider", y.displayName + y.providerId)
            }

            Log.e("Provider2", it.providerId)

            user = User(

                id = it.uid,
                email = it.email,
                provider = Provider.EMAIL_AND_PASSWORD,
                name = it.displayName,
                verifiedEmail = it.isEmailVerified,
                photo = it.photoUrl,
                phoneNumber = it.phoneNumber

            )

        }

        return user

    }

    override fun getGoogleUserData(): User? {

        var user : User? = null

        GoogleSignIn.getLastSignedInAccount(context)?.let {

            user = User(

                id = it.idToken!!,
                email = it.email,
                provider = Provider.GOOGLE,
                name = it.displayName,
                verifiedEmail = true,
                photo = it.photoUrl,
                phoneNumber = null

            )


        }

        return user

    }

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseAuthResource<AuthResult> {
        return try {

            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            FirebaseAuthResource.Success(result)

        }catch (e : Exception){

            FirebaseAuthResource.Failure(e)

        }

    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseAuthResource<AuthResult> {
       return try {
           val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
           FirebaseAuthResource.Success(result!!)
       }catch (e :Exception) {

            FirebaseAuthResource.Failure(e)
       }
    }

    override suspend fun loginWithGoogleAccount(task : Task<GoogleSignInAccount>) : FirebaseAuthResource<GoogleSignInAccount> {
       return if (task.isSuccessful){

           val account = task.result
           val credential = GoogleAuthProvider.getCredential(account.idToken, null)
           firebaseAuth.signInWithCredential(credential)

           FirebaseAuthResource.Success(task.result)

       }else{

           FirebaseAuthResource.Failure(task.exception!!)

       }
    }


    override fun logout() {

        firebaseAuth.signOut()
        googleSignInClient.signOut()

    }


}