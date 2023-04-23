package com.sergio.gymlog.data.model.remote.firestore

import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.MuscularGroup
import com.sergio.gymlog.data.model.training.Training

data class TrainingCloud(

    val id : String = "",
    val name : String = "",
    val description : String = "",
    val muscularGroups : List<MuscularGroup> = emptyList(),
    val exercises : List<ReferencedExercises> = emptyList()

){

    fun toTraining(exercises: List<Exercises.TrainingExercise>) : Training{
        return Training(this.id,this.name,this.description,this.muscularGroups,exercises)
    }

}


