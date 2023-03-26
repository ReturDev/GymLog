package com.sergio.gymlog.ui.main


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.User
import com.sergio.gymlog.databinding.ActivityMainBinding
import com.sergio.gymlog.util.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            mainViewModel.manageUserData()
        }
        setCollector()

    }

    private fun setCollector() {
        lifecycleScope.launch{

            repeatOnLifecycle(Lifecycle.State.STARTED){

                mainViewModel.uiState.collect{currentState ->

                    if (currentState.loading){

                        binding.pbMainLoading.visibility = View.VISIBLE
                        binding.fragmentNavMain.visibility = View.GONE

                    }else {

                        binding.pbMainLoading.visibility = View.GONE
                        binding.fragmentNavMain.visibility = View.VISIBLE

                    }

                    if (currentState.errorResource != null){

                        this@MainActivity.toast(getString(currentState.errorResource), Toast.LENGTH_LONG)
                        mainViewModel.logout()

                    }

                }
            }
        }
    }

}