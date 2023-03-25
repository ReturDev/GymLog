package com.sergio.gymlog.data.authentication



import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sergio.gymlog.User
import com.sergio.gymlog.data.model.FirebaseResource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseAuthenticationService @Inject constructor(
    private  val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
    ) : FirebaseAuthentication {


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
                username = it.displayName,
                verifiedEmail = it.isEmailVerified,
                photo = it.photoUrl

            )

        }

        return user

    }

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseResource<AuthResult> {
        return try {

            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            FirebaseResource.Success(result)

        }catch (e : Exception){

            FirebaseResource.Failure(e)

        }

    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseResource<AuthResult> {
       return try {
           val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
           FirebaseResource.Success(result!!)
       }catch (e :Exception) {

            FirebaseResource.Failure(e)
       }
    }

    override suspend fun loginWithGoogleAccount(task : Task<GoogleSignInAccount>) : FirebaseResource<GoogleSignInAccount> {
       return if (task.isSuccessful){

           val account = task.result
           val credential = GoogleAuthProvider.getCredential(account.idToken, null)
           firebaseAuth.signInWithCredential(credential)

           FirebaseResource.Success(task.result)

       }else{

           FirebaseResource.Failure(task.exception!!)

       }
    }


    override fun logout() {

        firebaseAuth.signOut()
        googleSignInClient.signOut()

    }

}