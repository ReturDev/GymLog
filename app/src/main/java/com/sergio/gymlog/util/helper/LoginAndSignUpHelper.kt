package com.sergio.gymlog.util.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

class LoginAndSignUpHelper {

    fun enableButtonListener(editText: EditText, otherEditText: EditText, button: Button): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                button.isEnabled = editText.text.isNotBlank() && otherEditText.text.isNotBlank()

            }

            override fun afterTextChanged(s: Editable?) {}

        }

    }

}