package com.sergio.gymlog.data.remote.authentication



import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseAuthenticationService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
    ) : FirebaseAuthentication {


    override suspend fun loginWithEmailAndPassword(email: String,password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email,password)
    }

    override suspend fun signUpWithEmailAndPassword(email: String,password: String): Task<AuthResult> {
       return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun loginWithGoogleAccount(task : Task<GoogleSignInAccount>){

           val account = task.result
           val credential = GoogleAuthProvider.getCredential(account.idToken, null)
           firebaseAuth.signInWithCredential(credential)

    }

    override fun logout() {

        firebaseAuth.signOut()
        googleSignInClient.signOut()

    }

    override fun sendPasswordRecoveryEmail(email: String): Task<Void> {
        return firebaseAuth.sendPasswordResetEmail(email)
    }

}