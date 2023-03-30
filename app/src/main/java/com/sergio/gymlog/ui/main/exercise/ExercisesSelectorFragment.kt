package com.sergio.gymlog.ui.main.exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.sergio.gymlog.databinding.FragmentExercisesSelectorBinding
import com.sergio.gymlog.ui.main.exercise.adapter.ExercisesSelectorAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ExercisesSelectorFragment : Fragment() {

    private lateinit var binding : FragmentExercisesSelectorBinding
    private lateinit var adapter: ExercisesSelectorAdapter

    private val exercisesSelectorVM  by viewModels<ExercisesSelectorViewModel>()
    private val args : ExercisesSelectorFragmentArgs by navArgs()

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
        setCollector()
        setListeners()
    }

    private fun initRecyclerView() {
        adapter = ExercisesSelectorAdapter(exercisesSelectorVM.uiState.value.exercises, onClickElement = {position, selected -> onClickElement(position, selected)})
    }

    private fun setCollector() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                exercisesSelectorVM.uiState.collect{currentState->

                    if (!currentState.loaded){

                        exercisesSelectorVM.loadExercises(args.idsExercises)

                    }

                }

            }

        }

    }

    private fun setListeners() {
        binding.btnAddES.setOnClickListener {  }
    }

    private fun onClickElement(position : Int, selected : Boolean){



    }







}