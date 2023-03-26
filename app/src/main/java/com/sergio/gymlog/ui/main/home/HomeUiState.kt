package com.sergio.gymlog.ui.main.home

import com.sergio.gymlog.data.model.User

data class HomeUiState(

    val refresh : Boolean = true,
    val dailyTraining : User.DailyTraining? = null

)
