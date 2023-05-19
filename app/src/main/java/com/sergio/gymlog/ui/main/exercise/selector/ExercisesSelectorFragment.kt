package com.sergio.gymlog.ui.main.exercise.selector

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
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
import com.sergio.gymlog.data.model.exercise.Equipment
import com.sergio.gymlog.data.model.exercise.MuscularGroup
import com.sergio.gymlog.databinding.FragmentExercisesSelectorBinding
import com.sergio.gymlog.ui.main.exercise.selector.adapter.ExercisesSelectorAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ExercisesSelectorFragment() : Fragment(), FilterExercisesListener {

    private lateinit var binding : FragmentExercisesSelectorBinding
    private lateinit var adapter: ExercisesSelectorAdapter
    private lateinit var bottomMenu : BottomNavigationView
    override var filterNumbers: Int = 0

    private val exercisesSelectorVM  by viewModels<ExercisesSelectorViewModel>()
    private val args : ExercisesSelectorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bottomMenu = requireActivity().findViewById(R.id.bottomNavigationView)
        bottomMenu.visibility = View.GONE
        exercisesSelectorVM.loadExercises(args.idsExercises)

        binding = FragmentExercisesSelectorBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setCollector()
        setListeners()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setCollector() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                exercisesSelectorVM.uiState.collect{currentState->

                    if (currentState.refresh){

                        adapter.exercisesList = currentState.exercises
                        adapter.notifyDataSetChanged()
                        binding.btnAddES.text = getString(R.string.add_quantity, currentState.exercisesSelectedQuantity)
                        binding.exercisesListIncluded.tvExercisesListNumberLabel.text = getString(R.string.number_of_exercises, currentState.exercises.size)
                        val filterButtonText = if (filterNumbers == 0) R.string.filter else R.string.number_of_filters
                        binding.exercisesListIncluded.btnExerciseListFilter.text = getString(filterButtonText)
                        exercisesSelectorVM.exerciseStatusChanged()

                    }
                    if (currentState.exerciseChangedPosition != -1){

                        adapter.notifyItemChanged(currentState.exerciseChangedPosition)
                        binding.btnAddES.text = getString(R.string.add_quantity, currentState.exercisesSelectedQuantity)
                        exercisesSelectorVM.exerciseStatusChanged()

                    }
                    if (currentState.idExercisesToAdd.isNotEmpty()){

                        val action =
                            ExercisesSelectorFragmentDirections.actionExercisesSelectorFragmentToTrainingEditorFragment(
                                currentState.idExercisesToAdd.toTypedArray()
                            )
                        findNavController().navigate(action)
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

            exercisesSelectorVM.addSelectedExercises()

        }

        binding.btnCancelES.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.exercisesListIncluded.btnExercisesListCreateExercise.setOnClickListener{

            val action = ExercisesSelectorFragmentDirections.actionGlobalExerciseCreatorFragment()
            findNavController().navigate(action)

        }

        binding.exercisesListIncluded.etExerciseListSearcher.doOnTextChanged { text, _ , _ , _ ->
            exercisesSelectorVM.filter(text.toString())
        }

    }

    private fun onClickElement(position : Int){

        if (adapter.exercisesList[position].selected){

            exercisesSelectorVM.deselectExercise(position)

        }else{

            exercisesSelectorVM.selectExercise(position)

        }

        //TODO("Acabar")

    }

    override fun filter(filterNumbers: Int, userExercises : Boolean, equipments : List<Equipment>, muscularGroups : List<MuscularGroup>) {
        super.filter(filterNumbers, userExercises, equipments, muscularGroups)

        exercisesSelectorVM.filter()

    }

}