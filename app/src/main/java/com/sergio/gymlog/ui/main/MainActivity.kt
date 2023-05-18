package com.sergio.gymlog.ui.main


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.ActivityMainBinding
import com.sergio.gymlog.ui.access.AccessActivity
import com.sergio.gymlog.util.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configNavBottomMenu()

        auth.addAuthStateListener {

            if (it.currentUser == null){

                val intent = Intent(this, AccessActivity::class.java)
                this.startActivity(intent)

            }
        }

        lifecycleScope.launch {
            mainViewModel.manageUserData()
        }
        setCollector()

    }

    private fun configNavBottomMenu(){

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnItemReselectedListener { item ->

            val navGraph  = navController.graph.findNode(item.itemId) as NavGraph
            navController.popBackStack(navGraph.startDestinationId, inclusive = false)
        }

    }

    private fun setCollector() {
        lifecycleScope.launch{

            repeatOnLifecycle(Lifecycle.State.STARTED){

                mainViewModel.uiState.collect{currentState ->

                    if (currentState.loading){

                        binding.pbMainLoading.visibility = View.VISIBLE
                        binding.navHostFragment.visibility = View.GONE
                        binding.bottomNavigationView.visibility = View.GONE

                    }else {

                        binding.pbMainLoading.visibility = View.GONE
                        binding.navHostFragment.visibility = View.VISIBLE
                        binding.bottomNavigationView.visibility = View.VISIBLE

                    }

                    if (currentState.errorResource != null){

                        this@MainActivity.toast(currentState.errorResource, Toast.LENGTH_LONG)
                        //TODO si entra aquí, cerrar la sesión y volver automaticamente a la Activity de Access, introduciendo un dialog con el mensaje superior.

                    }

                }
            }
        }
    }

}