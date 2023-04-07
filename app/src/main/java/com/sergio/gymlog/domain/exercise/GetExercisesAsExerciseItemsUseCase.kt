package com.sergio.gymlog.domain.exercise

import com.sergio.gymlog.data.model.exercise.ExerciseItem
import com.sergio.gymlog.data.model.exercise.Exercises
import javax.inject.Inject

class GetExercisesAsExerciseItemsUseCase @Inject constructor(
    private val getAllExercisesUseCase: GetAllExercisesUseCase
) {

    suspend operator fun invoke(exercisesSelected: Array<String>) : MutableList<ExerciseItem>{

        val allExercises = getAllExercisesUseCase()
        val exerciseItems = mutableListOf<ExerciseItem>()

        for (exercise in allExercises) {
            if (exercise is Exercises.ProvidedExercise) {
                if (!exercisesSelected.contains(exercise.id)) {
                    exerciseItems.add(ExerciseItem(exercise))
                }
            } else if (exercise is Exercises.UserExercise) {
                if (!exercisesSelected.contains(exercise.id)) {
                    exerciseItems.add(ExerciseItem(exercise))
                }
            }

        }

        return exerciseItems

    }

}