package com.sergio.gymlog.ui.main


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.ActivityMainBinding
import com.sergio.gymlog.util.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //https://developer.android.com/guide/navigation/navigation-ui?hl=es-419
        //https://developer.android.com/guide/navigation/multi-back-stacks?hl=es-419#nav-xml
        configNavBottomMenu()

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

            supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

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

                        this@MainActivity.toast(getString(currentState.errorResource), Toast.LENGTH_LONG)
                        //TODO si entra aquí, cerrar la sesión y volver automaticamente a la Activity de Access, introduciendo un dialog con el mensaje superior.

                    }

                }
            }
        }
    }

}