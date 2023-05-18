package com.sergio.gymlog.data.model.user


import com.google.firebase.Timestamp
import com.sergio.gymlog.data.model.training.Training


data class UserInfo constructor(

    var id : String = "",
    var email : String = "",
    var username : String = "",
    var photo : String = "",
    var weight : Double = 0.0,
    var dailyTraining: DailyTraining? = null,
    var sets : Int = 3,
    var repetitions : Int = 10
){

    data class DailyTraining (

        val date : Timestamp? = null ,
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

    companion object{
        const val DAILY_TRAINING_TAG = "dailyTraining"
        const val USERNAME_TAG = "username"
        const val PHOTO_TAG = "photo"
        const val WEIGHT_TAG = "weight"
        const val SETS_TAG = "sets"
        const val REPETITIONS_TAG = "repetitions"
    }

}
