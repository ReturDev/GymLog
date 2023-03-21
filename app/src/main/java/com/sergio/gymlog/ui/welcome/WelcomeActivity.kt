package com.sergio.gymlog.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.sergio.gymlog.databinding.ActivityWelcomeBinding
import com.sergio.gymlog.ui.main.MainActivity
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    @Inject
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth.addAuthStateListener {
            if (it.currentUser != null){

                userLogged()

            }
        }
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun userLogged(){

        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)

    }

}