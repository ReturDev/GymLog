package com.sergio.gymlog.domain.training

import com.google.firebase.Timestamp
import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.data.repository.user.TrainingsRepository
import com.sergio.gymlog.domain.training.daily.GetDailyTrainingUseCase
import com.sergio.gymlog.domain.training.daily.SetDailyTrainingUseCase
import java.util.Date
import javax.inject.Inject

class ModifyTrainingUseCase @Inject constructor(
    private val trainingsRepository: TrainingsRepository,
    private val transformTrainingToTrainingCloudUseCase: TransformTrainingToTrainingCloudUseCase,
    private val getUserTrainingsUseCase: GetUserTrainingsUseCase,
    private val applicationData: ApplicationData,
    private val setDailyTrainingUseCase: SetDailyTrainingUseCase,
    private val getDailyTrainingUseCase: GetDailyTrainingUseCase
) {

    suspend operator fun invoke(newTraining : Training){

        val newTrainingCloud = transformTrainingToTrainingCloudUseCase(newTraining)

        val trainings = getUserTrainingsUseCase()
        val oldTrainingPost : Int = trainings.indexOfFirst { it.id == newTraining.id }

        trainingsRepository.modifyUserTraining(applicationData.userInfo.id, newTrainingCloud)
        applicationData.userTrainings[oldTrainingPost] = newTraining


        val dailyTraining = getDailyTrainingUseCase()

        if (dailyTraining?.training?.id == newTraining.id){

            setDailyTrainingUseCase(UserInfo.DailyTraining(Timestamp(Date()), newTraining))

        }


    }

}