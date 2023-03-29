package com.sergio.gymlog.ui.main.exercise

import com.sergio.gymlog.data.model.Exercises

data class ExercisesSelectorUiState(

    val refresh : Boolean = true,
    val exercises : List<Exercises> = emptyList(),

)