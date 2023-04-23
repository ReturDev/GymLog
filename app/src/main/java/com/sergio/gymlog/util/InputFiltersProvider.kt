package com.sergio.gymlog.util

import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.widget.EditText

object InputFiltersProvider {

    fun repetitionsFilter() = InputFilter { source, start, end, dest, dstart, dend ->

        val sb = StringBuilder(dest)
        sb.replace(dstart, dend, source.subSequence(start, end).toString())
        val temp = sb.toString()

        if (temp.length > 2){
            return@InputFilter ""
        }
        if (temp.isNotBlank() && temp[0] == '0'){
            return@InputFilter ""
        }

        return@InputFilter null

    }

    fun weightFilter(etWeight : EditText) = InputFilter { source, start, end, dest, dstart, dend ->

        val sb = StringBuilder(dest)
        sb.replace(dstart, dend, source.subSequence(start, end).toString())
        val temp = sb.toString()

        if (temp == ".") {
            return@InputFilter "0."
        }else if (temp.isNotBlank() && temp[0] == '0' && temp.length > 1 && temp[1] != '.'){
            etWeight.text = SpannableStringBuilder(source)
            etWeight.setSelection(end)

        }

        val indexPoint = temp.indexOf(".")
        if (indexPoint == -1) {
            if (temp.length == 4 ) {
                return@InputFilter ".$source"
            }else if (temp.length > 5){
                return@InputFilter ""
            }
        } else {
            val intPart = temp.substring(0, indexPoint)
            val decimalPart = temp.substring(indexPoint + 1)

            if (intPart.length > 3 || decimalPart.length > 2) {
                return@InputFilter ""
            }
        }

        return@InputFilter null
    }

    fun usernameFilter() = InputFilter { source, start, end, dest, dstart, dend ->

        val sb = StringBuilder(dest)
        sb.replace(dstart, dend, source.subSequence(start, end).toString())
        val temp = sb.toString()

        if (temp.length > 50){
            return@InputFilter ""
        }

        return@InputFilter null

    }

    fun descriptionFilter() = InputFilter { source, start, end, dest, dstart, dend ->

        val sb = StringBuilder(dest)
        sb.replace(dstart, dend, source.subSequence(start, end).toString())
        val temp = sb.toString()

        if (temp.length > 250){
            return@InputFilter ""
        }

        return@InputFilter null
    }

}