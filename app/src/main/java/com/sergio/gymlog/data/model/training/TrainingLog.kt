package com.sergio.gymlog.data.model.training

import com.google.firebase.Timestamp

data class TrainingLog(
    val id : String = "",
    val date : Timestamp? = null,
    val training : TrainingOfTrainingLog? = null
)
