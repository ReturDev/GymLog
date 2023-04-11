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
import com.sergio.gymlog.data.model.exercise.TrainingExerciseSet
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.databinding.FragmentTrainingEditorBinding
import com.sergio.gymlog.ui.main.training.editor.adapter.TrainingEditorAdapter
import com.sergio.gymlog.util.TrainingEdit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrainingEditorFragment : Fragment() {

    private lateinit var binding : FragmentTrainingEditorBinding
    private lateinit var adapter : TrainingEditorAdapter

    private val trainingEditorViewModel by viewModels<TrainingEditorViewModel>()
    private val args by navArgs<TrainingEditorFragmentArgs>()
    private var argsGet : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTrainingEditorBinding.inflate(layoutInflater, container, false)

        if (!argsGet){
            trainingEditorViewModel.loadTrainingData(args.idTraining, args.idsExercises)
            argsGet = true
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setCollectors()

    }

    private fun setListeners() {

        binding.btnEditorCancel.setOnClickListener {

            //TODO añadir dialog
            findNavController().popBackStack()
        }

        binding.btnEditorAccept.setOnClickListener {

            //TODO añadir dialog
            //TODO Terminar
            TrainingEdit.setTraining(null)

        }

        binding.btnEditorAddExercises.setOnClickListener {

            TrainingEdit.setTraining(getTrainingInfo())
            val idsExercises = trainingEditorViewModel.uiState.value.training.exercises.map { e -> e.id }.toTypedArray()
            val action = TrainingEditorFragmentDirections.actionTrainingEditorFragmentToExercisesSelectorFragment(idsExercises)
            findNavController().navigate(action)

        }

        binding.btnEditorFilter.setOnClickListener {



        }

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

                        bindTrainingData(currentState.training)
                        initRecyclerView(currentState.training.exercises)

                        trainingEditorViewModel.resetValues()

                    }

                    if(currentState.removedExercisePosition != -1){

                        adapter.trainingExercises.removeAt(currentState.removedExercisePosition)
                        adapter.notifyItemRemoved(currentState.removedExercisePosition)
                        trainingEditorViewModel.resetValues()

                    }

                    if (currentState.deletedExerciseSetPosition != -1){

                        val holder = binding.rvEditorExercises.findViewHolderForAdapterPosition(currentState.changedExercisePosition) as TrainingEditorAdapter.TrainingEditorHolder
                        holder.deleteExerciseSet(currentState.deletedExerciseSetPosition)
                        trainingEditorViewModel.resetValues()
                    }

                    if (currentState.exerciseSetInserted){

                        val holder = binding.rvEditorExercises.findViewHolderForAdapterPosition(currentState.changedExercisePosition) as TrainingEditorAdapter.TrainingEditorHolder
                        holder.addExerciseSet(currentState.newExerciseSet!!)
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

        adapter = TrainingEditorAdapter(
            trainingExercises = exercises.toMutableList(),
            onRemoveExercise = { position -> onRemoveExercise(position)},
            onAddExerciseSet = { exercisePos  -> onAddExerciseSet(exercisePos)},
            onDeleteExerciseSet = {
                    exercisePosition,
                    exerciseSetPosition ->
                onDeleteExerciseSet(exercisePosition,exerciseSetPosition)
            },
            onExerciseSetValueChanged = {
                    exerciseID,
                    exerciseSetPos,
                    trainingSet ->
                onExerciseSetValueChanged(exerciseID,exerciseSetPos, trainingSet)
            }
        )
        val recyclerView = binding.rvEditorExercises
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

    }

    private fun onExerciseSetValueChanged(
        exerciseID: String,
        exerciseSetPos: Int,
        trainingSet: TrainingExerciseSet
    ) {
        TODO() //Implementar y luego probarlo todo
    }


    private fun onRemoveExercise(position : Int){

        trainingEditorViewModel.removeExercise(adapter.trainingExercises[position], position)

    }

    private fun onAddExerciseSet(exercisePos : Int){

        trainingEditorViewModel.addExerciseSet(
            exercisePos,
            adapter.trainingExercises[exercisePos].id,
        )

    }

    private fun onDeleteExerciseSet(exercisePosition : Int, setPosition : Int){

        trainingEditorViewModel.deleteExerciseSet(
            exercisePosition,
            adapter.trainingExercises[exercisePosition].id,
            setPosition
        )

    }

    private fun getTrainingInfo() : Training{

        return trainingEditorViewModel.uiState.value.training.copy(
            name = binding.etEditorName.text.toString(),
            description = binding.etEditorDescription.text.toString()
        )

    }

}