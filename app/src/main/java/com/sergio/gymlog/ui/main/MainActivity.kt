package com.sergio.gymlog.ui.main


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sergio.gymlog.data.authentication.FirebaseAuthenticationRepository
import com.sergio.gymlog.databinding.ActivityMainBinding
import com.sergio.gymlog.ui.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            firebaseAuthenticationRepository.logout()
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()

        }


        Log.e("Provider2", firebaseAuthenticationRepository.checkUserLogged().toString())

        firebaseAuthenticationRepository.getUserData()

    }

    @Inject
    lateinit var firebaseAuthenticationRepository : FirebaseAuthenticationRepository


}