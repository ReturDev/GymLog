package com.sergio.gymlog.ui.main.exercise.creator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergio.gymlog.data.model.exercise.MuscularGroup
import com.sergio.gymlog.databinding.DialogSelectMuscularGroupBinding

class ExerciseCreatorSelectMuscularGroupDialog : BottomSheetDialogFragment() {

    private lateinit var binding : DialogSelectMuscularGroupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogSelectMuscularGroupBinding.inflate(inflater, container, false)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

    }

    private fun setListeners() {

        binding.btnSelectMuscularGBack.setOnClickListener { sendEquipmentSelected(MuscularGroup.BACK) }
        binding.btnSelectMuscularGChest.setOnClickListener { sendEquipmentSelected(MuscularGroup.CHEST) }
        binding.btnSelectMuscularGShoulders.setOnClickListener { sendEquipmentSelected(MuscularGroup.SHOULDERS) }
        binding.btnSelectMuscularGLegs.setOnClickListener { sendEquipmentSelected(MuscularGroup.LEGS) }

    }

    private fun sendEquipmentSelected(muscularGroup : MuscularGroup){

        val action = ExerciseCreatorSelectMuscularGroupDialogDirections.actionExerciseCreatorSelectMuscularGroupDialogToExerciseCreatorFragment(muscularGroupSelected = muscularGroup)
        findNavController().navigate(action)

    }

}