package com.sergio.gymlog.util.extension

import android.app.Activity
import android.widget.Toast

fun Activity.toast(textReference : Int, length : Int = Toast.LENGTH_SHORT){

    Toast.makeText(this, getString(textReference), length).show()

}