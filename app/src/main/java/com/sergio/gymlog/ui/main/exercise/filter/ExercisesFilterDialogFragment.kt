package com.sergio.gymlog.ui.main.exercise.filter

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Equipment
import com.sergio.gymlog.data.model.exercise.MuscularGroup
import com.sergio.gymlog.databinding.DialogExercisesFilterBinding
import com.sergio.gymlog.ui.main.exercise.creator.ChangeDataListener
import com.sergio.gymlog.ui.main.exercise.creator.ExerciseCreatorSelectEquipmentDialog
import com.sergio.gymlog.ui.main.exercise.creator.ExerciseCreatorSelectMuscularGroupDialog
import com.sergio.gymlog.ui.main.exercise.selector.FilterExercisesListener


class ExercisesFilterDialogFragment(
    private val listener : FilterExercisesListener,
    private var equipmentFilter : Equipment,
    private var muscularGroupFilter : MuscularGroup,
    private var customExercisesFilter : Boolean
) : BottomSheetDialogFragment(), ChangeDataListener {

    lateinit var binding : DialogExercisesFilterBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogExercisesFilterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind()
        setListeners()
    }

    private fun bind() {

        if (equipmentFilter != Equipment.NONE){
            binding.tvEquipmentFiltersSelected.text = getString(equipmentFilter.stringResource)
        }

        if (muscularGroupFilter != MuscularGroup.NONE){
            binding.tvMuscularGroupFiltersSelected.text = getString(muscularGroupFilter.stringResource)
        }

        binding.switchUserExercises.isChecked = customExercisesFilter

    }

    private fun setListeners() {

        binding.equipmentFilterLayout.setOnClickListener {

            ExerciseCreatorSelectEquipmentDialog(this).show(parentFragmentManager, "filter_equipment_dialog")

        }

        binding.muscularGroupFilterLayout.setOnClickListener {

            ExerciseCreatorSelectMuscularGroupDialog(this).show(parentFragmentManager, "filter_muscular_dialog")

        }

        binding.customExercisesFilterLayout.setOnClickListener {

            binding.switchUserExercises.isChecked = !binding.switchUserExercises.isChecked

        }

        binding.switchUserExercises.setOnCheckedChangeListener { _ , isChecked ->
            customExercisesFilter = isChecked
        }

        binding.btnExercisesFilterReset.setOnClickListener {
            reset()
        }

    }

    private fun reset() {
        listener.resetFilters()
        muscularGroupFilter = MuscularGroup.NONE
        equipmentFilter = Equipment.NONE
        customExercisesFilter = false
        dismiss()
    }

    override fun changeEquipment(equipment: Equipment) {
        binding.tvEquipmentFiltersSelected.text = getString(equipment.stringResource)
        equipmentFilter = equipment
    }

    override fun changeMuscularGroup(muscularGroup: MuscularGroup) {
        binding.tvMuscularGroupFiltersSelected.text = getString(muscularGroup.stringResource)
        muscularGroupFilter = muscularGroup
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        listener.filter(customExercisesFilter, equipmentFilter, muscularGroupFilter)


    }

}