package com.sergio.gymlog.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.User
import com.sergio.gymlog.databinding.FragmentHomeBinding
import com.sergio.gymlog.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setListeners()
        setCollector()
    }

    private fun setCollector() {
        lifecycleScope.launch{

            repeatOnLifecycle(Lifecycle.State.STARTED){

                homeViewModel.uiState.collect{currentState ->

                    if (currentState.dailyTraining){

                        binding.trainingNotSelectedLayout.visibility = View.GONE
                        binding.trainingSelectedLayout.visibility = View.VISIBLE

                    }else{

                        binding.trainingNotSelectedLayout.visibility = View.VISIBLE
                        binding.trainingSelectedLayout.visibility = View.GONE
                        binding.trainingName.text = "Entrenamiento"

                    }


                }


            }


        }
    }

    private fun setListeners() {
        binding.btnCompleteTraining.setOnClickListener {  }
        binding.btnRemoveTraining.setOnClickListener {  }
        binding.btnModifyTaining.setOnClickListener {  }
        binding.trainingNotSelectedLayout.setOnClickListener {  }
    }


}