package com.sergio.gymlog.domain.training

import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.repository.user.TrainingsRepository
import javax.inject.Inject

class DeleteTrainingUseCase @Inject constructor(
    private val applicationData: ApplicationData,
    private val trainingsRepository: TrainingsRepository
) {

    suspend operator fun invoke(trainingID : String){

        trainingsRepository.deleteUserTraining(applicationData.userInfo.id, trainingID)

        val position = applicationData.userTrainings.indexOfFirst { it.id == trainingID }

        applicationData.userTrainings.removeAt(position)

    }

}