package com.sergio.gymlog.ui.main.exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sergio.gymlog.databinding.FragmentExercisesSelectorBinding


class ExercisesSelectorFragment : Fragment() {

    private lateinit var binding : FragmentExercisesSelectorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExercisesSelectorBinding.inflate(layoutInflater, container,false)

        return binding.root
    }



}