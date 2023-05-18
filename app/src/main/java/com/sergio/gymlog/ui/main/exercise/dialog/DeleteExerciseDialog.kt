package com.sergio.gymlog.ui.main.exercise.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.DialogDeleteExerciseBinding
import com.sergio.gymlog.ui.main.training.DeleteTrainingListener


class DeleteExerciseDialog(
    private val listener : DeleteTrainingListener,
    private val elementPosition : Int,
    private val messageResource : Int
) : BottomSheetDialogFragment() {

    private lateinit var binding : DialogDeleteExerciseBinding
    lateinit var countDownTimer : CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogDeleteExerciseBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countDownTimer = object : CountDownTimer(4000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.btnDeleteExerciseDialogDelete.text = getString(R.string.delete_with_time, millisUntilFinished/1000)

            }

            override fun onFinish() {
                binding.btnDeleteExerciseDialogDelete.text = getString(R.string.delete)
                binding.btnDeleteExerciseDialogDelete.isEnabled = true
            }

        }

        binding.tvDeleteExerciseDialogMessage.text = getString(messageResource)

        binding.btnDeleteExerciseDialogDelete.isEnabled = false
        countDownTimer.start()

        binding.btnDeleteExerciseDialogDelete.setOnClickListener {
            dialog?.dismiss()
            listener.onClickDelete(elementPosition)
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        countDownTimer.cancel()
    }


}