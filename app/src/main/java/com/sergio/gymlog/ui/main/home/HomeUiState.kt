package com.sergio.gymlog.ui.main.home

import com.sergio.gymlog.data.model.UserInfo

data class HomeUiState(

    val refresh : Boolean = true,
    val dailyTraining : UserInfo.DailyTraining? = null,
    val errorResource : Int? = null

)
