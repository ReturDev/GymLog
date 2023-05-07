package com.sergio.gymlog.ui.main.home.selector

import com.sergio.gymlog.data.model.training.Training

data class DailyTrainingSelectorUiState (
    val loading : Boolean = false,
    val loaded : Boolean = false,
    val trainings : List<Training> = emptyList()
)