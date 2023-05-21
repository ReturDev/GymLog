package com.sergio.gymlog.ui.main.exercise.creator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergio.gymlog.data.model.exercise.Equipment
import com.sergio.gymlog.databinding.DialogSelectEquipmentBinding

class ExerciseCreatorSelectEquipmentDialog(
    private val listener: ChangeDataListener
) : BottomSheetDialogFragment(){

    private lateinit var binding : DialogSelectEquipmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogSelectEquipmentBinding.inflate(inflater, container, false)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

    }

    private fun setListeners() {

        binding.btnSelectEquipmentBarbell.setOnClickListener { sendEquipmentSelected(Equipment.BARBELL) }
        binding.btnSelectEquipmentDumbbells.setOnClickListener { sendEquipmentSelected(Equipment.DUMBBELLS) }
        binding.btnSelectEquipmentCable.setOnClickListener { sendEquipmentSelected(Equipment.CABLE) }
        binding.btnSelectEquipmentEzBar.setOnClickListener { sendEquipmentSelected(Equipment.EZ_BAR) }
        binding.btnSelectEquipmentBodyWeight.setOnClickListener { sendEquipmentSelected(Equipment.BODY_WEIGHT) }

    }

    private fun sendEquipmentSelected(equipment : Equipment){

        listener.changeEquipment(equipment)
        dismiss()

    }



}