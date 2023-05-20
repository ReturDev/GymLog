package com.sergio.gymlog.ui.main.exercise.creator

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Equipment
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.MuscularGroup
import com.sergio.gymlog.databinding.FragmentExerciseCreatorBinding
import com.sergio.gymlog.util.InputFiltersProvider
import com.sergio.gymlog.util.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExerciseCreatorFragment : Fragment() {

    private lateinit var binding : FragmentExerciseCreatorBinding
    private val exerciseCreatorViewModel by viewModels<ExerciseCreatorViewModel>()
    private val args by navArgs<ExerciseCreatorFragmentArgs>()

    private lateinit var equipment: Equipment
    private lateinit var muscularGroup: MuscularGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExerciseCreatorBinding.inflate(inflater, container, false)
        val bottomMenu = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomMenu.visibility = View.GONE

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        equipment = args.equipmentSelected
        muscularGroup = args.muscularGroupSelected

        exerciseCreatorViewModel.loadCreatingExerciseInfo()
        setButtonsSelectors()

        setCollector()
        setListeners()

    }

    private fun setCollector() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                exerciseCreatorViewModel.uiState.collect{currentState ->

                    if (currentState.creatingExerciseData != null){

                        binding.etExerciseCreatorName.text = SpannableStringBuilder(currentState.creatingExerciseData.name)
                        binding.etExerciseCreatorDescription.text = SpannableStringBuilder(currentState.creatingExerciseData.description)

                        if (currentState.creatingExerciseData.muscularGroup != MuscularGroup.NONE){
                            muscularGroup = currentState.creatingExerciseData.muscularGroup
                        }

                        if (currentState.creatingExerciseData.equipment != Equipment.NONE){
                            equipment = currentState.creatingExerciseData.equipment
                        }


                        setButtonsSelectors()

                    }

                    if (currentState.saved){

                        exerciseCreatorViewModel.resetCreatingExercise()
                        findNavController().popBackStack()

                    }

                }

            }
        }

    }

    private fun setListeners() {

        binding.etExerciseCreatorName.filters = arrayOf(InputFiltersProvider.usernameFilter())
        binding.etExerciseCreatorDescription.filters = arrayOf(InputFiltersProvider.descriptionFilter())

        binding.btnExerciseCreatorEquipment.setOnClickListener {

            exerciseCreatorViewModel.setCreatingExercise(getExerciseInfo())
            findNavController().navigate(R.id.action_exerciseCreatorFragment_to_exerciseCreatorSelectEquipmentDialog)

        }

        binding.btnExerciseCreatorMuscularG.setOnClickListener {

            exerciseCreatorViewModel.setCreatingExercise(getExerciseInfo())
            findNavController().navigate(R.id.action_exerciseCreatorFragment_to_exerciseCreatorSelectMuscularGroupDialog)

        }

        binding.btnExerciseCreatorCancel.setOnClickListener {

            exerciseCreatorViewModel.resetCreatingExercise()
            findNavController().popBackStack()

        }

        binding.btnExerciseCreatorCreate.setOnClickListener {

            if (binding.etExerciseCreatorName.text!!.isBlank()){

                requireActivity().toast(R.string.exercise_name_required)

            }else if (equipment == Equipment.NONE){

                requireActivity().toast(R.string.exercise_equipment_required)

            }else if (muscularGroup == MuscularGroup.NONE){

                requireActivity().toast(R.string.exercise_muscular_group_required)

            }else {

                exerciseCreatorViewModel.createNewExercise(getExerciseInfo())

            }

        }
    }

    private fun getExerciseInfo() = Exercises.UserExercise(

        name = binding.etExerciseCreatorName.text.toString(),
        description = binding.etExerciseCreatorDescription.text.toString(),
        equipment = equipment,
        muscularGroup = muscularGroup

    )

    private fun setButtonsSelectors(){

        if (equipment != Equipment.NONE){
            binding.btnExerciseCreatorEquipment.text = getString(equipment.stringResource)
            binding.btnExerciseCreatorEquipment.setCompoundDrawablesWithIntrinsicBounds(null, ResourcesCompat.getDrawable(resources,equipment.iconResource, null), null,null)
        }

        if (muscularGroup != MuscularGroup.NONE){
            binding.btnExerciseCreatorMuscularG.text = getString(muscularGroup.stringResource)
            binding.btnExerciseCreatorMuscularG.setCompoundDrawablesWithIntrinsicBounds(null, ResourcesCompat.getDrawable(resources,muscularGroup.iconResource, null), null,null)
        }

    }

}