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
import androidx.navigation.fragment.findNavController
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentHomeBinding
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

                    if (currentState.refresh){

                        if (currentState.dailyTraining != null){

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
    }

    private fun setListeners() {
        binding.btnCompleteTraining.setOnClickListener {  }
        //binding.btnRemoveTraining.setOnClickListener { homeViewModel.removeDailyTraining() }
        binding.btnModifyTaining.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_modifyDailyTrainingFragment)  }
        binding.trainingNotSelectedLayout.setOnClickListener {  }



    }


}