package com.sergio.gymlog.ui.main.exercise.creator

import com.sergio.gymlog.data.model.exercise.Exercises

data class ExerciseCreatorUiState(
    val creatingExerciseData : Exercises.UserExercise? = null,
    val saved : Boolean = false
)