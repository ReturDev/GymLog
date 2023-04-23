package com.sergio.gymlog.data.model.training

import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.MuscularGroup
import com.sergio.gymlog.data.model.remote.firestore.ReferencedExercises
import com.sergio.gymlog.data.model.remote.firestore.TrainingCloud


data class Training(

    val id : String = "",
    val name : String = "",
    val description : String = "",
    val muscularGroups: List<MuscularGroup> = emptyList(),
    val exercises : List<Exercises.TrainingExercise> = emptyList()

){

    fun toTrainingCloud(exercises: List<ReferencedExercises>) : TrainingCloud{

        return  TrainingCloud(this.id, this.name, this.description,this.muscularGroups, exercises)

    }

}
