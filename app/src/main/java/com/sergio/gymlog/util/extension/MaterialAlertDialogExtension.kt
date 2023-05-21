package com.sergio.gymlog.util.extension

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.DialogChangeUserDataBinding
import com.sergio.gymlog.util.InputFiltersProvider

fun MaterialAlertDialogBuilder.userDataChangeDialog(
    titleResource : Int,
    currentValue: String,
    units : Boolean,
    acceptAction: (newValue: String) -> Unit,
    context: Context
){

    val binding = DialogChangeUserDataBinding.inflate(LayoutInflater.from(context))

    binding.etChangeUDataValue.addTextChangedListener {it ->

        it?.let {

            binding.btnChangeUDataAccept.isEnabled = it.isNotBlank()

        }


    }


    val dialog = this.setView(binding.root)
        .setCancelable(true)
        .setTitle(titleResource)
        .create()


    binding.etChangeUDataValue.text = SpannableStringBuilder(currentValue)

    if (units){
        binding.etChangeUDataValue.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        binding.tilChangeUDataValue.suffixText = context.getString(R.string.kg)
        binding.etChangeUDataValue.filters = arrayOf(InputFiltersProvider.weightFilter(binding.etChangeUDataValue))
    }else{
        binding.etChangeUDataValue.filters = arrayOf(InputFiltersProvider.repetitionsFilter())
    }


    binding.btnChangeUDataAccept.setOnClickListener {

        val newValue = binding.etChangeUDataValue.text.toString()

        if (currentValue != newValue){
            acceptAction(newValue)
        }

        dialog.dismiss()

    }

    binding.btnChangeUDataCancel.setOnClickListener {

        dialog.dismiss()

    }

    dialog.show()

}

fun AlertDialog.Builder.alertDialog(positiveButtonListener : DialogInterface.OnClickListener, negativeButtonListener : DialogInterface.OnClickListener){

    this.setCancelable(false)
        .setMessage(R.string.cancel_daily_training_completed)
        .setNegativeButton(R.string.cancel, negativeButtonListener)
        .setPositiveButton(R.string.accept, positiveButtonListener)
        .show()

}