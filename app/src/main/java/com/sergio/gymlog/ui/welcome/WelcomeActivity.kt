package com.sergio.gymlog.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.sergio.gymlog.User
import com.sergio.gymlog.data.firestore.CloudFirestoreService
import com.sergio.gymlog.databinding.ActivityWelcomeBinding
import com.sergio.gymlog.ui.main.MainActivity
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    @Inject
    lateinit var auth : FirebaseAuth
    @Inject
    lateinit var cloudFirestoreService: CloudFirestoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth.addAuthStateListener {
            if (it.currentUser != null){

                lifecycleScope.launch {
                    if(!cloudFirestoreService.existUser(it.currentUser!!.uid)){

                        cloudFirestoreService.createNewUser(
                            User(
                                id = it.currentUser!!.uid,
                                username = it.currentUser!!.displayName,
                                photo = it.currentUser!!.photoUrl,
                                email = it.currentUser!!.email
                            )
                        )

                    }
                }
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