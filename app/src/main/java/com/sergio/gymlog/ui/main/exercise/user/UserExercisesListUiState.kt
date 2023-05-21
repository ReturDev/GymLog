package com.sergio.gymlog.ui.main.exercise.user

import com.sergio.gymlog.data.model.exercise.Exercises

data class UserExercisesListUiState(
    val loading : Boolean = false,
    val loaded : Boolean = false,
    val userExercises : List<Exercises.UserExercise> = emptyList(),
    val exerciseDeletedPos : Int = -1
)
