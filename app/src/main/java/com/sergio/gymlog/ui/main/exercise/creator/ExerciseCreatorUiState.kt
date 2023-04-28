package com.sergio.gymlog.ui.main.exercise.creator

import com.sergio.gymlog.data.model.exercise.Equipment
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.MuscularGroup

data class ExerciseCreatorUiState(
    val creatingExerciseData : Exercises.UserExercise? = null,
    val saved : Boolean = false
)