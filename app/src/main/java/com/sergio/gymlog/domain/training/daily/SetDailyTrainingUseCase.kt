package com.sergio.gymlog.domain.training.daily

import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.data.repository.user.UserDataRepository
import javax.inject.Inject

class SetDailyTrainingUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val applicationData: ApplicationData
) {

    suspend operator fun invoke(dailyTraining: UserInfo.DailyTraining){

        applicationData.userInfo.dailyTraining = dailyTraining
        userDataRepository.setDailyTraining(applicationData.userInfo.id, dailyTraining)

    }

}