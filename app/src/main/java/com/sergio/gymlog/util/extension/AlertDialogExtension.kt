package com.sergio.gymlog.util.extension

import android.app.AlertDialog
import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.sergio.gymlog.databinding.DialogChangeUserDataBinding
import com.sergio.gymlog.util.InputFiltersProvider

fun AlertDialog.Builder.userDataChangeDialog(
    titleResource : Int,
    currentValue: String,
    units : Boolean,
    acceptAction: (newValue: String) -> Unit,
    context: Context
){

    val binding = DialogChangeUserDataBinding.inflate(LayoutInflater.from(context))

    binding.etChangeUDataValue.addTextChangedListener {it ->

        it?.let {

            binding.ivChangeUDataAccept.isEnabled = it.isNotBlank()

        }


    }


    val dialog = this.setView(binding.root)
        .setCancelable(true)
        .setTitle(context.getString(titleResource))
        .create()


    binding.etChangeUDataValue.text = SpannableStringBuilder(currentValue)

    if (units){
        binding.etChangeUDataValue.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        binding.tvChangeUDataKg.visibility = View.VISIBLE
        binding.etChangeUDataValue.filters = arrayOf(InputFiltersProvider.weightFilter(binding.etChangeUDataValue))
    }else{
        binding.etChangeUDataValue.filters = arrayOf(InputFiltersProvider.repetitionsFilter())
    }


    binding.ivChangeUDataAccept.setOnClickListener {

        val newValue = binding.etChangeUDataValue.text.toString()

        if (currentValue != newValue){
            acceptAction(newValue)
        }

        dialog.dismiss()

    }

    binding.ivChangeUDataCancel.setOnClickListener {

        dialog.dismiss()

    }

    dialog.show()

}