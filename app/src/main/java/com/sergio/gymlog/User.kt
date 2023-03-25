package com.sergio.gymlog

import android.net.Uri
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sergio.gymlog.data.model.Provider
import java.sql.Timestamp

data class User(

    val id : String,
    val email : String?,
    var username : String?,
    var photo : Uri?,
    var verifiedEmail : Boolean = false,
    var weight : Int = 0,
    var dailyTraining: DailyTraining? = null,
    var sets : Int = 3,
    var repetitions : Int = 0

){

    data class DailyTraining(

        val date : FieldValue = FieldValue.serverTimestamp(),
        val training: Training

    )


}
