package com.sergio.gymlog.domain.record

import android.util.Log
import com.google.firebase.Timestamp
import com.sergio.gymlog.data.model.training.TrainingLog
import com.sergio.gymlog.data.repository.user.RecordRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetTrainingLogByDateUseCase @Inject constructor(
    private val getTrainingLogsUseCase: GetTrainingLogsUseCase
) {

    suspend operator fun invoke(date : Date) : List<TrainingLog> {

        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateFormatted = formatter.format(date)

        return getTrainingLogsUseCase()
            .filter { log ->  formatter.format(log.date!!.toDate()).compareTo(dateFormatted) == 0}


    }

}