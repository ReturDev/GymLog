package com.sergio.gymlog.domain.record

import com.google.firebase.Timestamp
import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.data.model.training.TrainingLog
import com.sergio.gymlog.data.model.training.TrainingOfTrainingLog
import com.sergio.gymlog.data.repository.user.RecordRepository
import com.sergio.gymlog.domain.training.daily.RemoveDailyTrainingUseCase
import com.sergio.gymlog.domain.user.GetUserInfoUseCase
import java.util.Date
import javax.inject.Inject

class CreateTrainingLogUseCase @Inject constructor(
    private val recordRepository: RecordRepository,
    private val removeDailyTrainingUseCase: RemoveDailyTrainingUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val applicationData: ApplicationData
) {

    suspend operator fun invoke(training : TrainingOfTrainingLog){

        val id = recordRepository.generateTrainingLogId(getUserInfoUseCase().id)
        val trainingLog = TrainingLog(
            id = id,
            date= Timestamp(Date()),
            training = training,
        )

        recordRepository.createTrainingLog(getUserInfoUseCase().id, trainingLog )

        applicationData.userTrainingLogs.add(trainingLog)
        applicationData.userTrainingLogs.sortByDescending { selector -> selector.date }


        removeDailyTrainingUseCase()

    }

}