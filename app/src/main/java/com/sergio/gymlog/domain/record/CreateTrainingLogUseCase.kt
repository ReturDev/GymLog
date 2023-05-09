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
    private val getUserInfoUseCase: GetUserInfoUseCase
) {

    suspend operator fun invoke(training : TrainingOfTrainingLog){

        val id = recordRepository.generateTrainingLogId(getUserInfoUseCase().id)

        recordRepository.createTrainingLog(getUserInfoUseCase().id, TrainingLog(
            id = id,
            date= Timestamp(Date()),
            training = training,
        ))

        removeDailyTrainingUseCase()

    }

}