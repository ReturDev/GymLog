package com.sergio.gymlog.ui.main.exercise

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergio.gymlog.R
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

        exercisesSelectorVM.loadExercises(args.idsExercises)

        binding = FragmentExercisesSelectorBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCollector()
        setListeners()
    }

    private fun setCollector() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                exercisesSelectorVM.uiState.collect{currentState->

                    if (currentState.loaded){

                        initRecyclerView()

                    }
                    if (currentState.refresh){

                        adapter.exercisesList = currentState.exercises
                        adapter.notifyDataSetChanged()
                        exercisesSelectorVM.refreshed()

                    }
                    if (currentState.exerciseChangedPosition != -1){

                        adapter.notifyItemChanged(currentState.exerciseChangedPosition)
                        binding.btnAddES.text = getString(R.string.add_quantity, currentState.exercisesSelectedQuantity)
                        exercisesSelectorVM.exerciseStatusChanged()

                    }

                }

            }

        }

    }

    private fun initRecyclerView() {
        adapter = ExercisesSelectorAdapter(exercisesSelectorVM.uiState.value.exercises, onClickElement = {position -> onClickElement(position)})
        val recycler = binding.exercisesListIncluded.rvExercisesList
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
    }

    private fun setListeners() {
        binding.btnAddES.setOnClickListener {



        }
    }

    private fun onClickElement(position : Int){

        if (adapter.exercisesList[position].selected){

            exercisesSelectorVM.deselectExercise(position)

        }else{

            exercisesSelectorVM.selectExercise(position)

        }

    }

}