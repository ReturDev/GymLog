package com.sergio.gymlog.domain.training

import com.sergio.gymlog.data.model.remote.firestore.ReferencedExercises
import com.sergio.gymlog.data.model.remote.firestore.TrainingCloud
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.domain.exercise.GetExerciseReferenceUseCase
import javax.inject.Inject

class TransformTrainingToTrainingCloudUseCase @Inject constructor(
    private val getExerciseReferenceUseCase: GetExerciseReferenceUseCase
){

    suspend operator fun invoke(training : Training) : TrainingCloud{

        val exerciseReferences = mutableListOf<ReferencedExercises>()

        for (exercise in training.exercises){

            exerciseReferences.add(ReferencedExercises(
                getExerciseReferenceUseCase(exercise),
                exercise.sets)
            )

        }

        return training.toTrainingCloud(exerciseReferences)

    }

}