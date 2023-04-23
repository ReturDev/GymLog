package com.sergio.gymlog.data.model.repository

import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.training.Training
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
data class ApplicationData private constructor(

    val userInfo: UserInfo,
    val providedExercises : MutableList<Exercises.ProvidedExercise>,
    val userExercises: MutableList<Exercises.UserExercise>,
    val userTrainings : MutableList<Training>,
    var userExercisesConsulted : Boolean,
    var userTrainingsConsulted : Boolean

    ){
    @Inject constructor() : this(UserInfo(), mutableListOf(), mutableListOf(), mutableListOf(), false, false)

    fun clear(){
        userInfo.apply {
            this.id = ""
            this.email = ""
            this.username = ""
            this.photo = ""
            this.weight = 0.0
            this.dailyTraining = null
            this.sets = 3
            this.repetitions = 10
        }

        providedExercises.clear()
        userExercises.clear()
        userTrainings.clear()
        userExercisesConsulted = false
        userTrainingsConsulted = false

    }

}