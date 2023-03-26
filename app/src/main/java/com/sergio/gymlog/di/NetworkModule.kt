package com.sergio.gymlog.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.User
import com.sergio.gymlog.util.helper.CloudFirestoreHelper
import com.sergio.gymlog.util.helper.LoginAndSignUpHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideFirebaseAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideGoogleSignInOptions(@ApplicationContext context: Context) : GoogleSignInOptions{

        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext context: Context, googleSignInOptions: GoogleSignInOptions) : GoogleSignInClient{
        return GoogleSignIn.getClient(context, googleSignInOptions)
    }


}