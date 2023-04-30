package com.sergio.gymlog.ui.main.exercise.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergio.gymlog.databinding.DialogExerciseClickedBinding
import com.sergio.gymlog.ui.main.exercise.ExerciseDialogListener


class ExerciseClickedDialog(
    private val listener : ExerciseDialogListener,
    private val exercisePos : Int
) : BottomSheetDialogFragment() {

    private lateinit var binding : DialogExerciseClickedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogExerciseClickedBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

    }

    private fun setListeners() {

        binding.btnExerciseClickedDialogInformation.setOnClickListener {
            dialog?.dismiss()
            listener.onClickInformation(exercisePos)
        }

        binding.btnExerciseClickedDialogDelete.setOnClickListener {
            dialog?.dismiss()
            val confirmDeleteDialog = DeleteExerciseDialog(listener,exercisePos)
            confirmDeleteDialog.show(parentFragmentManager, "confirm_exercise_delete")
        }

    }

}