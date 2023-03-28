package com.sergio.gymlog.ui.main.training.dailytraining

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import androidx.fragment.app.viewModels
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentModifyDailyTrainingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ModifyDailyTrainingFragment : Fragment() {

    private lateinit var binding: FragmentModifyDailyTrainingBinding
    private val mdtViewModel by viewModels<ModifyDailyTrainingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModifyDailyTrainingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        binding.btnEditDailyTraining.setOnClickListener {  }

    }

}