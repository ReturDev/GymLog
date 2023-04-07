package com.sergio.gymlog.data.model.remote.firestore

import com.google.firebase.firestore.DocumentReference
import com.sergio.gymlog.data.model.training.TrainingSet

data class ReferencedExercises(

    val reference : DocumentReference? = null,
    val sets : List<TrainingSet> = emptyList()

)
