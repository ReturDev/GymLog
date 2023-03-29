package com.sergio.gymlog.ui.main.training

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentTrainingEditorBinding
import com.sergio.gymlog.ui.main.exercise.ExercisesSelectorFragmentDirections

class TrainingEditorFragment : Fragment() {

    private lateinit var binding : FragmentTrainingEditorBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrainingEditorBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnClickListener {
            val action = TrainingEditorFragmentDirections.actionTrainingEditorFragmentToExercisesSelectorFragment(
                emptyArray()
            )
            findNavController().navigate(action)

        }
    }


}