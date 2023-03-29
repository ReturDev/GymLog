package com.sergio.gymlog.ui.main.training

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentTrainingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrainingFragment : Fragment() {

    private lateinit var binding: FragmentTrainingBinding
    private val trainingViewModel by viewModels<TrainingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrainingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvD.setOnClickListener { findNavController().navigate(R.id.action_trainingFragment_to_trainingEditorFragment) }
    }


}