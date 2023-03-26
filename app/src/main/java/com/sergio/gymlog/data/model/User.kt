package com.sergio.gymlog.data.model

import android.net.Uri
import com.google.firebase.firestore.Exclude
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class User constructor(

    @Exclude @JvmField var id : String,
    var email : String,
    var username : String,
    var photo : Uri?,
    @Exclude @JvmField var verifiedEmail : Boolean,
    var weight : Int,
    var dailyTraining: DailyTraining?,
    var sets : Int,
    var repetitions : Int
){

    @Inject constructor(
    ) : this(
        "",
        "",
        "",
        null,
        false,
        0,
        null,
        3,
        10)
    data class DailyTraining @Inject constructor(

        val date : Date,
        val training: Training

    )


}
