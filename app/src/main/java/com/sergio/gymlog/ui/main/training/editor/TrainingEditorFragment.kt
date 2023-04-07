package com.sergio.gymlog.ui.main.training.editor

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.databinding.FragmentTrainingEditorBinding
import com.sergio.gymlog.ui.main.training.editor.adapter.TrainingEditorAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrainingEditorFragment : Fragment() {

    private lateinit var binding : FragmentTrainingEditorBinding
    private lateinit var adapter : TrainingEditorAdapter

    private val trainingEditorViewModel by viewModels<TrainingEditorViewModel>()
    private val args by navArgs<TrainingEditorFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTrainingEditorBinding.inflate(layoutInflater, container, false)

        if (args.idTraining.isNotBlank()){
            trainingEditorViewModel.loadTrainingData(args.idTraining)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setCollectors()

    }

    private fun setListeners() {

        binding.btnEditorCancel.setOnClickListener { findNavController().popBackStack() }

        binding.btnEditorAccept.setOnClickListener {  }

        binding.btnEditorAddExercises.setOnClickListener {

            //val exID =
            //val action = TrainingEditorFragmentDirections.actionTrainingEditorFragmentToExercisesSelectorFragment()

        }

        binding.btnEditorFilter.setOnClickListener {  }

        binding.etEditorName.addTextChangedListener{}
        binding.etEditorDescription.addTextChangedListener {  }
        binding.etEditorSearcher.addTextChangedListener{}

    }

    private fun setCollectors() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                trainingEditorViewModel.uiState.collect{ currentState ->

                    if (currentState.loading){

                        binding.editorElementsRoot.visibility = View.GONE
                        binding.pbTrainingEditorLoading.visibility = View.VISIBLE

                    }

                    if (currentState.loaded){

                        binding.editorElementsRoot.visibility = View.VISIBLE
                        binding.pbTrainingEditorLoading.visibility = View.GONE

                        currentState.training?.let {

                            bindTrainingData(it)
                            initRecyclerView(currentState.training.exercises)
                            trainingEditorViewModel.resetValues()

                        }

                    }

                    if(currentState.removedExercisePosition != -1){

                        adapter.trainingExercises = currentState.training!!.exercises
                        adapter.notifyItemRemoved(currentState.removedExercisePosition)
                        trainingEditorViewModel.resetValues()

                    }

                }
            }
        }
    }

    private fun bindTrainingData(training: Training) {

        binding.etEditorName.text = SpannableStringBuilder(training.name)
        binding.etEditorDescription.text = SpannableStringBuilder(training.description)

    }

    private fun initRecyclerView(exercises : List<Exercises.TrainingExercise>){

        adapter = TrainingEditorAdapter(exercises, onRemoveExercise = { position -> onRemoveExercise(position)})
        val recyclerView = binding.rvEditorExercises
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

    }

    private fun onRemoveExercise(position : Int){

        trainingEditorViewModel.removeExercise(position)

    }



}