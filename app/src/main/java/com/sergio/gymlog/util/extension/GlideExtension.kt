package com.sergio.gymlog.util.extension

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sergio.gymlog.R

fun RequestManager.setImageRoundedBorders(image : Any, imageView: ImageView){
    val cornerRadius = imageView.context.resources.getDimensionPixelSize(R.dimen.rounded_corners)

    this.load(image)
        .transform(RoundedCorners(cornerRadius))
        .into(imageView)
}