package com.sergio.gymlog.domain.training

import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.data.repository.user.TrainingsRepository
import javax.inject.Inject

class CreateUserTrainingUseCase @Inject constructor(
    private val trainingsRepository: TrainingsRepository,
    private val applicationData: ApplicationData,
    private val transformTrainingToTrainingCloudUseCase: TransformTrainingToTrainingCloudUseCase
) {

    suspend operator fun invoke(trainingWithoutID: Training){

        val trainingWithID = trainingWithoutID.copy(id = trainingsRepository.generateTrainingRandomId(applicationData.userInfo.id))

        trainingsRepository.createUserTraining(
            applicationData.userInfo.id,
            transformTrainingToTrainingCloudUseCase(trainingWithID)
        )

        applicationData.userTrainings.add(trainingWithID)

    }

}