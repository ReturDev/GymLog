package com.sergio.gymlog.ui.main.training.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.databinding.FragmentTrainingDetailsBinding
import com.sergio.gymlog.ui.main.exercise.dialog.DeleteExerciseDialog
import com.sergio.gymlog.ui.main.training.DeleteTrainingListener
import com.sergio.gymlog.ui.main.training.detail.adapter.TrainingDetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrainingDetailsFragment : Fragment(), DeleteTrainingListener {

    private val args by navArgs<TrainingDetailsFragmentArgs>()
    private val trainingDetailViewModel by viewModels<TrainingDetailViewModel>()

    private lateinit var adapter : TrainingDetailsAdapter
    private lateinit var binding : FragmentTrainingDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingDetailsBinding.inflate(inflater, container, false)
        trainingDetailViewModel.loadTraining(args.idTraining)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCollector()
        setListeners()
    }

    private fun initRecyclerView(exercises: List<Exercises.TrainingExercise>) {
        adapter = TrainingDetailsAdapter(exercises.toMutableList())
        val recycler = binding.rvTrainingExercises
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun setCollector() {

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){

                trainingDetailViewModel.uiState.collect{currentState ->

                    if (currentState.training != null){

                        initRecyclerView(currentState.training.exercises)
                        bindTraining(currentState.training)

                    }
                    if (currentState.trainingDeleted){
                        findNavController().popBackStack()
                    }

                }

            }
        }

    }

    private fun bindTraining(training: Training) {

        binding.tvTrainingDetailName.text = training.name
        binding.tvTrainingDescription.text = training.description

        if (training.description.isNotBlank()){
            binding.tvTrainingDescription.visibility = View.VISIBLE
        } else {
            binding.tvTrainingDescription.visibility = View.GONE
        }
    }

    private fun setListeners() {

        binding.btnDeleteTraining.setOnClickListener { //TODO Insertar opción para eliminar entrenamiento.
            DeleteExerciseDialog(
                listener = this,
                elementPosition = -1,
                R.string.delete_training
            ).show(
                parentFragmentManager,
                "delete_exercise_dialog"
                )
         }

        binding.btnModifyTraining.setOnClickListener {
            val action = TrainingDetailsFragmentDirections.actionGlobalTrainingEditorFragment(idTraining = trainingDetailViewModel.uiState.value.training!!.id, idsExercises = emptyArray<String>())
            findNavController().navigate(action)
        }

    }

    override fun onClickDelete(position: Int) {
        trainingDetailViewModel.deleteTraining()
    }


}