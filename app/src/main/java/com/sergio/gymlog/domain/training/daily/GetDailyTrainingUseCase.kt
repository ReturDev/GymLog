package com.sergio.gymlog.domain.training.daily

import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.data.repository.user.UserDataRepository
import com.sergio.gymlog.domain.user.GetUserInfoUseCase
import javax.inject.Inject

class GetDailyTrainingUseCase @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) {

    suspend operator fun invoke () : UserInfo.DailyTraining?{

        var dailyTraining = getUserInfoUseCase().dailyTraining

        if (dailyTraining != null) {

            val timestampActual = System.currentTimeMillis()
            val timestampLimit = dailyTraining.date!!.toDate().time + (24 * 60 * 60 * 1000)


            dailyTraining = if (timestampActual > timestampLimit) null else dailyTraining
        }


        return dailyTraining

    }

}