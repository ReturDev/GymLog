package com.sergio.gymlog.ui.main.training.dailytraining

import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.data.model.user.UserInfo

data class ModifyDailyTrainingUiState(
    val loading : Boolean = false,
    val loaded : Boolean = false,
    val selectedTraining : UserInfo.DailyTraining? = null,
    val trainingSelected : Boolean = false,
    val trainings : List<Training> = emptyList()
)
