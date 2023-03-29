package com.sergio.gymlog.data.model

import javax.inject.Inject


data class ApplicationData private constructor(

    val userInfo: UserInfo,
    val userExercises : MutableList<Exercises.UserExercise>,
    val userTrainings : MutableList<Training>,
    val providedExercise: MutableList<Exercises.ProvidedExercise>

){
    @Inject constructor() : this(UserInfo(), mutableListOf(), mutableListOf(), mutableListOf())

}