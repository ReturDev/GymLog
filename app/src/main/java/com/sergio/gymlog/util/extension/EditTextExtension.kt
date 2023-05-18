package com.sergio.gymlog.util.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

fun EditText.buttonActivationOnTextChanged(button : Button, vararg othersEditText : EditText){

    this.addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (othersEditText.isEmpty()){

                button.isEnabled = s.toString().isNotBlank()

            }else{

                button.isEnabled = s.toString().isNotBlank() && othersEditText.none { et -> et.text.isBlank() }

            }
        }


    })

}