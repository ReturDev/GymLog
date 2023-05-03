package com.sergio.gymlog.domain.record

import com.sergio.gymlog.data.repository.user.RecordRepository
import javax.inject.Inject

class CreateTrainingLogUseCase @Inject constructor(
    private val recordRepository: RecordRepository
) {


}