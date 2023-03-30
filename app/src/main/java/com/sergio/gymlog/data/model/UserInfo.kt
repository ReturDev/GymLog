package com.sergio.gymlog.data.model


import com.google.firebase.firestore.Exclude
import java.util.*
import javax.inject.Inject


data class UserInfo constructor(

    var id : String = "",
    var email : String = "",
    var username : String = "",
    var photo : String = "",
    var weight : Int = 0,
    var dailyTraining: DailyTraining? = null,
    var sets : Int = 3,
    var repetitions : Int = 10
){

    data class DailyTraining (

        val date : Date? = null ,
        val training : Training? = null

    )

    fun setAllData(user : UserInfo){
        this.id = user.id
        this.username = user.username
        this.email = user.email
        this.dailyTraining = user.dailyTraining
        this.photo = user.photo
        this.repetitions = user.repetitions
        this.sets = user.sets
        this.weight = user.weight
    }


}
