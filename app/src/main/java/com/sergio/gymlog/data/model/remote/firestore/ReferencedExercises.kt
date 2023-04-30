package com.sergio.gymlog.data.model.remote.firestore

import com.google.firebase.firestore.DocumentReference
import com.sergio.gymlog.data.model.exercise.TrainingExerciseSet

data class ReferencedExercises(

    val reference : DocumentReference? = null,
    val sets : List<TrainingExerciseSet> = emptyList()

){
    companion object{
        const val REFERENCE_TAG = "reference"
    }
}
