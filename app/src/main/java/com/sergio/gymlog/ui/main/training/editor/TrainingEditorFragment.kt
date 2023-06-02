package com.sergio.gymlog.ui.main.training.editor

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.MuscularGroup
import com.sergio.gymlog.data.model.exercise.TrainingExerciseSet
import com.sergio.gymlog.data.model.temporal.EditingTraining
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.databinding.FragmentTrainingEditorBinding
import com.sergio.gymlog.ui.main.training.editor.adapter.TrainingEditorAdapter
import com.sergio.gymlog.util.InputFiltersProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrainingEditorFragment : Fragment() {

    private lateinit var binding : FragmentTrainingEditorBinding
    private lateinit var adapter : TrainingEditorAdapter
    private var bottomMenu : BottomNavigationView? = null

    private val trainingEditorViewModel by viewModels<TrainingEditorViewModel>()
    private val args by navArgs<TrainingEditorFragmentArgs>()
    private var argsGet : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTrainingEditorBinding.inflate(layoutInflater, container, false)

        bottomMenu = requireActivity().findViewById(R.id.bottomNavigationView)
        bottomMenu?.visibility = View.GONE

        setupOnBackPressed()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!argsGet){
            trainingEditorViewModel.loadTrainingData(args.idTraining, args.idsExercises)
            argsGet = true
        }
        initRecyclerView()
        setListeners()
        setCollectors()

    }

    private fun setListeners() {

        binding.btnEditorCancel.setOnClickListener {

            setupOnEditCanceled()

        }

        binding.btnEditorSave.setOnClickListener {
            val training = getTrainingInfo()

            if (training.exercises.isEmpty()){

                binding.editorExercisesErrorRoot.visibility = View.VISIBLE

            }else{

                binding.editorExercisesErrorRoot.visibility = View.GONE

            }

            if (training.name.isBlank()){

                binding.tilEditorName.error = getString(R.string.training_name_required)

            }

            if (training.name.isNotBlank() && training.exercises.isNotEmpty()){

                trainingEditorViewModel.saveTrainingData(getTrainingInfo())
                trainingEditorViewModel.resetEditingTraining()
                findNavController().popBackStack()

            }

        }

        binding.btnEditorAddExercises.setOnClickListener {

            trainingEditorViewModel.setEditingTraining(getTrainingInfo())
            val idsExercises = trainingEditorViewModel.uiState.value.training.exercises.map { e -> e.id }.toTypedArray()
            val action = TrainingEditorFragmentDirections.actionTrainingEditorFragmentToExercisesSelectorFragment(
                    idsExercises
                )
            findNavController().navigate(action)

        }

        binding.etEditorName.filters = arrayOf(InputFiltersProvider.usernameFilter())
        binding.etEditorName.addTextChangedListener {
            if (binding.tilEditorName.error != null){
                binding.tilEditorName.error = null
            }
        }
        binding.etEditorDescription.filters = arrayOf(InputFiltersProvider.descriptionFilter())

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

    @SuppressLint("NotifyDataSetChanged")
    private fun bindTrainingData(training: Training) {

        binding.etEditorName.text = SpannableStringBuilder(training.name)
        binding.etEditorDescription.text = SpannableStringBuilder(training.description)
        adapter.trainingExercises = training.exercises.toMutableList()
        adapter.notifyDataSetChanged()

    }

    private fun initRecyclerView(){

        adapter = TrainingEditorAdapter(
            trainingExercises = trainingEditorViewModel.uiState.value.training.exercises.toMutableList(),
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
        exerciseSet: TrainingExerciseSet
    ) {

        trainingEditorViewModel.onExerciseSetValueChanged(exerciseID, exerciseSetPos, exerciseSet)

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
            description = binding.etEditorDescription.text.toString(),
            muscularGroups = getMuscularGroups()
        )

    }

    private fun getMuscularGroups(): List<MuscularGroup> {
        val muscularGroups = MuscularGroup.values()

        val trainingMuscularGroups = mutableListOf<MuscularGroup>()

        for (muscularGroup in muscularGroups){

            if (muscularGroup != MuscularGroup.NONE){
                val contains = trainingEditorViewModel.uiState.value.training.exercises.map { ex ->
                    ex.muscularGroup
                }.any { mg ->
                    mg == muscularGroup
                }

                if (contains){
                    trainingMuscularGroups.add(muscularGroup)
                }
            }

        }

        return trainingMuscularGroups.toList()

    }

    private fun setupOnBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback (this){
            setupOnEditCanceled()
        }

    }

    private fun setupOnEditCanceled(){

        lifecycleScope.launch {

            if (trainingEditorViewModel.trainingHasChanges(getTrainingInfo())){

                val dialog  =  TrainingEditorCancelDialog{

                    trainingEditorViewModel.resetEditingTraining()
                    findNavController().popBackStack()
                    trainingEditorViewModel.resetEditingTraining()

                }
                dialog.show(parentFragmentManager, "bottom_dialog")


            }else{
                findNavController().popBackStack()
                trainingEditorViewModel.resetEditingTraining()
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        bottomMenu?.visibility = View.VISIBLE
    }



}