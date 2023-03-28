package com.sergio.gymlog.data.model

data class ApplicationData (
    
    val userInfo : UserInfo? = null,
    val userExercises : List<Exercises.UserExercise> = emptyList(),
    val userTrainings : List<Training> = emptyList(),
    val providedExercises : List<Exercises.ProvidedExercise> = emptyList(),

    )