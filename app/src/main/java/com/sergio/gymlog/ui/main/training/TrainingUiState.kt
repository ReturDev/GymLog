package com.sergio.gymlog.ui.main.training

import com.sergio.gymlog.data.model.training.Training

data class TrainingUiState (

    val loading : Boolean = true,
    val loaded : Boolean = false,
    val trainings : List<Training> = emptyList()


)
