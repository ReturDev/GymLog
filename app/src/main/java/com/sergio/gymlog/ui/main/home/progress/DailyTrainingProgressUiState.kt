package com.sergio.gymlog.ui.main.home.progress

import com.sergio.gymlog.data.model.exercise.ExerciseItem
import com.sergio.gymlog.data.model.exercise.Exercises

data class DailyTrainingProgressUiState(
    val loaded : Boolean = false,
    val exercises : List<ExerciseItem> = emptyList(),
    val exerciseChangedPosition : Int = -1,
    val exercisesSelectedQuantity : Int = 0,
    val exercisesToAdd : List<Exercises.TrainingExercise> = emptyList()
)
