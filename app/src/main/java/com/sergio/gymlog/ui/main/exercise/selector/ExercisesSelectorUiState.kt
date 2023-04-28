package com.sergio.gymlog.ui.main.exercise.selector

import com.sergio.gymlog.data.model.exercise.ExerciseItem

data class ExercisesSelectorUiState(

    val loaded : Boolean = false,
    val refresh : Boolean = false,
    val exercises : List<ExerciseItem> = emptyList(),
    val idExercisesToAdd : List<String> = emptyList(),
    val exercisesSelectedQuantity : Int = 0,
    val exerciseChangedPosition : Int = -1

    )