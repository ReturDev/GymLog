package com.sergio.gymlog.domain.record

import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.model.training.TrainingLog
import com.sergio.gymlog.data.repository.user.RecordRepository
import javax.inject.Inject

class GetTrainingLogsUseCase @Inject constructor(
    private val applicationData: ApplicationData,
    private val recordRepository: RecordRepository
) {

    suspend operator fun invoke(): MutableList<TrainingLog> {

        if (applicationData.userTrainingLogs.isEmpty()){

            applicationData.userTrainingLogs.addAll(
                recordRepository.getTrainingLogs(applicationData.userInfo.id)
            )

        }

        return applicationData.userTrainingLogs

    }

}