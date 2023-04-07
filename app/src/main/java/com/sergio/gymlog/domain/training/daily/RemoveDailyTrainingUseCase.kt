package com.sergio.gymlog.domain.training.daily

import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.repository.user.UserDataRepository
import javax.inject.Inject

class RemoveDailyTrainingUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val applicationData: ApplicationData
) {

    suspend operator fun invoke(){

        applicationData.userInfo.dailyTraining = null
        userDataRepository.removeDailyTraining(applicationData.userInfo.id)

    }

}