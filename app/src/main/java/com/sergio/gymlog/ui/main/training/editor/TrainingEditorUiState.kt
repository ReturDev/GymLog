package com.sergio.gymlog.ui.main.training.editor

import com.sergio.gymlog.data.model.training.Training

data class TrainingEditorUiState(

    val loading : Boolean = false,
    val loaded : Boolean = false,
    val training : Training = Training(),
    val removedExercisePosition : Int = -1

)
