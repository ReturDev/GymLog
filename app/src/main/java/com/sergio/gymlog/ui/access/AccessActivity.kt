package com.sergio.gymlog.ui.access

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.sergio.gymlog.databinding.ActivityAccessBinding
import com.sergio.gymlog.ui.main.MainActivity
import com.sergio.gymlog.util.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
        auth.addAuthStateListener {

            it.currentUser?.let {

                userLogged()

            }

        }
        binding = ActivityAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

                        this@AccessActivity.toast(getString(currentState.errorResource))
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
        accessViewModel.loginWithGoogle(task)


    }

}