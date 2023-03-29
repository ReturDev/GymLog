package com.sergio.gymlog.ui.main.exercise

import com.sergio.gymlog.data.model.Exercises

data class ExercisesSelectorUiState(

    val loaded : Boolean = false,
    //val refresh : Boolean = true,
    val exercises : List<Exercises> = emptyList(),
    val idExercisesToAdd : List<String> = emptyList(),
    val exercisesSelectedSize : Int = 0

    )