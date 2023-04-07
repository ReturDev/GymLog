package com.sergio.gymlog.domain.training

import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.data.repository.user.TrainingsRepository
import javax.inject.Inject

class GetUserTrainingsUseCase @Inject constructor(

    private val trainingsRepository: TrainingsRepository,
    private val applicationData: ApplicationData,
    private val transformTrainingCloudToTrainingUseCase: TransformTrainingCloudToTrainingUseCase

) {

    suspend operator fun invoke() : List<Training>{

        if (!applicationData.userExercisesConsulted){

            val trainingsCloud = trainingsRepository.getUserTrainings(applicationData.userInfo.id)
            val trainings = mutableListOf<Training>()

            for (trainingCloud in trainingsCloud){

                trainings.add(transformTrainingCloudToTrainingUseCase(trainingCloud))

            }

            applicationData.userTrainings.addAll(trainings)
            applicationData.userTrainingsConsulted = true

        }

        return applicationData.userTrainings

    }

}