package com.sergio.gymlog.ui.main.training.editor

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergio.gymlog.databinding.DialogCancelEditBinding

class TrainingEditorCancelDialog(private val discardListener : View.OnClickListener) : BottomSheetDialogFragment() {

    private lateinit var binding : DialogCancelEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogCancelEditBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancelEditDialogDiscard.setOnClickListener{
            discardListener.onClick(it)
            dialog?.dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        // Hide the dialog border
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        return dialog
    }


}