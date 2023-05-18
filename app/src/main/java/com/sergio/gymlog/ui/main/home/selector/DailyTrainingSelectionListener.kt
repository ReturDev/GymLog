package com.sergio.gymlog.ui.main.home.selector

import com.sergio.gymlog.data.model.user.UserInfo

interface DailyTrainingSelectionListener {

    fun onTrainingSelected(dailyTraining: UserInfo.DailyTraining?)
    fun saveDailyTraining()

}