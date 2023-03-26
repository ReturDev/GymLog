package com.sergio.gymlog.data.model


import com.google.firebase.firestore.Exclude
import java.util.*


data class User constructor(

    @Exclude @JvmField var id : String = "",
    var email : String = "",
    var username : String = "",
    var photo : String = "",
    @Exclude @JvmField var verifiedEmail : Boolean = false,
    var weight : Int = 0,
    var dailyTraining: DailyTraining? = null,
    var sets : Int = 3,
    var repetitions : Int = 10
){

    data class DailyTraining (

        val date : Date? = null ,
        val training : Training? = null

    )


}
