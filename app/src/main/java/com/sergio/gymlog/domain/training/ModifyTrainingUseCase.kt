package com.sergio.gymlog.domain.training

import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.data.repository.user.TrainingsRepository
import javax.inject.Inject

class ModifyTrainingUseCase @Inject constructor(
    private val trainingsRepository: TrainingsRepository,
    private val transformTrainingToTrainingCloudUseCase: TransformTrainingToTrainingCloudUseCase,
    private val getUserTrainingsUseCase: GetUserTrainingsUseCase,
    private val applicationData: ApplicationData
) {

    suspend operator fun invoke(newTraining : Training){

        val newTrainingCloud = transformTrainingToTrainingCloudUseCase(newTraining)

        val trainings = getUserTrainingsUseCase()
        val oldTrainingPost : Int = trainings.indexOfFirst { it.id == newTraining.id }

        trainingsRepository.modifyUserTraining(applicationData.userInfo.id, newTrainingCloud)
        applicationData.userTrainings[oldTrainingPost] = newTraining

    }

}