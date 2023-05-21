package com.sergio.gymlog.ui.main.exercise.creator

import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ScaleDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
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
class ExerciseCreatorFragment : Fragment(), ChangeDataListener {

    private lateinit var binding : FragmentExerciseCreatorBinding
    private val exerciseCreatorViewModel by viewModels<ExerciseCreatorViewModel>()

    private var equipment: Equipment = Equipment.NONE
    private var muscularGroup: MuscularGroup = MuscularGroup.NONE

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

        setButtonsSelectors()

        setCollector()
        setListeners()

    }

    private fun setCollector() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                exerciseCreatorViewModel.uiState.collect{currentState ->

                    if (currentState.saved){

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

            ExerciseCreatorSelectEquipmentDialog(this).show(parentFragmentManager, "exercise_creator_select_equipment")

        }

        binding.btnExerciseCreatorMuscularG.setOnClickListener {

            ExerciseCreatorSelectMuscularGroupDialog(this).show(parentFragmentManager, "exercise_creator_select_muscular_group")

        }

        binding.btnExerciseCreatorCancel.setOnClickListener {

            findNavController().popBackStack()

        }

        binding.btnExerciseCreatorCreate.setOnClickListener {

            if (binding.etExerciseCreatorName.text!!.isBlank()){

                binding.tilExerciseCreatorName.error = getString(R.string.exercise_name_required)

            }

            if (equipment == Equipment.NONE){

                binding.exerciseCreatorEquipmentErrorRoot.visibility = View.VISIBLE

            }else{

                binding.exerciseCreatorEquipmentErrorRoot.visibility = View.GONE

            }

            if (muscularGroup == MuscularGroup.NONE){

                binding.exerciseCreatorMuscularGErrorRoot.visibility = View.VISIBLE

            }else {

                binding.exerciseCreatorMuscularGErrorRoot.visibility = View.GONE

            }

            if (binding.etExerciseCreatorName.text!!.isNotBlank() && equipment != Equipment.NONE && muscularGroup != MuscularGroup.NONE) {

                exerciseCreatorViewModel.createNewExercise(getExerciseInfo())

            }

        }

        binding.etExerciseCreatorName.addTextChangedListener {
            if (binding.tilExerciseCreatorName.error != null){
                binding.tilExerciseCreatorName.error = null
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

        setEquipmentData()
        setMuscularGData()

    }

    private fun setEquipmentData(){

        if (equipment != Equipment.NONE){
            binding.btnExerciseCreatorEquipment.text = getString(equipment.stringResource)
            val icon = ResourcesCompat.getDrawable(resources,equipment.iconResource, null)
            val layerDrawable = LayerDrawable(arrayOf(icon))
            layerDrawable.setLayerSize(0,100,100)
            binding.btnExerciseCreatorEquipment.setCompoundDrawablesWithIntrinsicBounds(null, layerDrawable , null,null)
        }

    }

    private fun setMuscularGData(){

        if (muscularGroup != MuscularGroup.NONE){
            binding.btnExerciseCreatorMuscularG.text = getString(muscularGroup.stringResource)
            val icon = ResourcesCompat.getDrawable(resources,muscularGroup.iconResource, null)
            val layerDrawable = LayerDrawable(arrayOf(icon))
            layerDrawable.setLayerSize(0,100,100)
            binding.btnExerciseCreatorMuscularG.setCompoundDrawablesWithIntrinsicBounds(null, layerDrawable , null,null)
        }

    }

    override fun changeEquipment(equipment: Equipment) {
        this.equipment = equipment
        setEquipmentData()
        binding.exerciseCreatorEquipmentErrorRoot.visibility = View.GONE
    }

    override fun changeMuscularGroup(muscularGroup: MuscularGroup) {
        this.muscularGroup = muscularGroup
        setMuscularGData()
        binding.exerciseCreatorMuscularGErrorRoot.visibility = View.GONE

    }

}