package com.sergio.gymlog.domain.training

import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.remote.firestore.TrainingCloud
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.domain.exercise.GetExerciseByIdUseCase
import javax.inject.Inject

class TransformTrainingCloudToTrainingUseCase @Inject constructor(
    private val getExerciseByIdUseCase: GetExerciseByIdUseCase,
) {

    suspend operator fun invoke(trainingCloud : TrainingCloud) : Training{

        val trainingExercises = mutableListOf<Exercises.TrainingExercise>()

        for (cloudEx in trainingCloud.exercises){

            val exercise = getExerciseByIdUseCase(cloudEx.reference!!.id).convertTo<Exercises.TrainingExercise>()
            exercise.sets = cloudEx.sets
            trainingExercises.add(exercise)

        }

        return trainingCloud.toTraining(trainingExercises)

    }

}