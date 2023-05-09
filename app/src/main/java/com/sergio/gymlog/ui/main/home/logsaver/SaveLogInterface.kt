package com.sergio.gymlog.ui.main.home.logsaver

import com.sergio.gymlog.data.model.training.TrainingOfTrainingLog

interface SaveLogInterface {

    fun saveLog(trainingOfTrainingLog: TrainingOfTrainingLog)

}