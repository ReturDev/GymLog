package com.sergio.gymlog.util.extension

import android.view.View
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar
import com.sergio.gymlog.R

fun View.createTopSnackBar(parent : View, messageResource : Int, duration: Int = Snackbar.LENGTH_SHORT) {
    val snackBar = Snackbar.make( parent, messageResource, duration)
    snackBar.setAction(R.string.accept) {
        snackBar.dismiss()     }

    val params = snackBar.view.layoutParams as FrameLayout.LayoutParams
    params.gravity = android.view.Gravity.TOP

    snackBar.view.layoutParams = params

    snackBar.show()

}