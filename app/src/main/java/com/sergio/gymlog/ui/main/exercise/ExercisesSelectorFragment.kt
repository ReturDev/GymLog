package com.sergio.gymlog.ui.main.exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sergio.gymlog.databinding.FragmentExercisesSelectorBinding
import javax.inject.Inject


class ExercisesSelectorFragment : Fragment() {

    private lateinit var binding : FragmentExercisesSelectorBinding
    private lateinit var adapter: ExercisesSelectorAdapter
    private val exercisesSelectorVM  by viewModels<ExercisesSelectorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExercisesSelectorBinding.inflate(layoutInflater, container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = ExercisesSelectorAdapter()
    }




}