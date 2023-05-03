package com.sergio.gymlog.ui.main.record

import com.sergio.gymlog.data.model.training.TrainingLog

data class RecordUiState (
    val loading : Boolean = false,
    val loaded : Boolean = false,
    val refresh : Boolean = false,
    val trainingLogs : List<TrainingLog> = emptyList()
)