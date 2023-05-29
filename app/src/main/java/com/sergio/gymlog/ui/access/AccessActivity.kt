package com.sergio.gymlog.ui.access

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.ActivityAccessBinding
import com.sergio.gymlog.ui.main.MainActivity
import com.sergio.gymlog.util.extension.createTopSnackBar
import com.sergio.gymlog.util.helper.SplashInitialization
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class AccessActivity : AppCompatActivity() {

    private val accessViewModel : AccessViewModel by viewModels()
    private lateinit var binding: ActivityAccessBinding
    @Inject
    lateinit var auth : FirebaseAuth
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var notChecked = true

        if (!SplashInitialization.initialized){

            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { notChecked }
            SplashInitialization.initialized = true

        }else{

            this.setTheme(R.style.Theme_GymLog)
            SplashInitialization.initialized = true
        }

        binding = ActivityAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        if(auth.currentUser != null){
//
//            userLogged()
//
//        }else{
//
//            auth.addAuthStateListener {
//
//                it.currentUser?.let {
//
//                    userLogged()
//
//                }
//
//            }
//
//        }

        auth.addAuthStateListener {

            it.currentUser?.let {

                userLogged()

            }

            notChecked = false

        }



        setCollector()
    }


    private fun setCollector(){

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED){

                accessViewModel.uiState.collect{ currentState ->

                    if (currentState.loading){

                        binding.pbAccesLoading.visibility = View.VISIBLE

                    }else{

                        binding.pbAccesLoading.visibility = View.GONE

                    }

                    if (currentState.errorResource != null){


                        binding.root.createTopSnackBar(binding.root, currentState.errorResource, Snackbar.LENGTH_LONG)
                        accessViewModel.errorMessageShown()

                    }

                }

            }

        }

    }

    private fun userLogged(){

        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)

    }

    fun googleAccess() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

        if (task.result != null){
            accessViewModel.loginWithGoogle(task)
        }


    }

}