package com.sergio.gymlog.domain.record

import com.sergio.gymlog.data.model.training.TrainingLog
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