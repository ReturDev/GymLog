package com.sergio.gymlog.ui.main.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.DialogOnLongClickTrainingBinding
import com.sergio.gymlog.ui.main.exercise.dialog.DeleteExerciseDialog


class OnLongClickTrainingDialog(

    private val position : Int,
    private val listener : DeleteTrainingListener

) : BottomSheetDialogFragment() {

    private lateinit var binding : DialogOnLongClickTrainingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogOnLongClickTrainingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOnLongClickTrainingDialogDelete.setOnClickListener {
            DeleteExerciseDialog(listener = listener, elementPosition = position, R.string.delete_training).show(parentFragmentManager, "delete_exercise_dialog")
            dismiss()
        }

    }


}