package com.sergio.gymlog.util.helper

import com.sergio.gymlog.data.model.exercise.Equipment
import com.sergio.gymlog.data.model.exercise.ExerciseItem
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.MuscularGroup

object FilterExercises {

    fun filter(
        name: String,
        userExercises: Boolean,
        equipment : Equipment,
        muscularGroup : MuscularGroup ,
        exercises : List<ExerciseItem>
    ): List<ExerciseItem> {

        var filteredExercises = exercises

        if (name.isNotBlank()) {

            filteredExercises = filterExercisesByName(name, filteredExercises)

        }

        if (userExercises) {


            filteredExercises = filterByUserExercises(filteredExercises)

        }

        if (equipment != Equipment.NONE) {

            filteredExercises = filterExercisesByEquipment(equipment, filteredExercises)

        }

        if (muscularGroup != MuscularGroup.NONE) {

            filteredExercises = filterExercisesByMuscularGroup(muscularGroup, filteredExercises)

        }

        return filteredExercises

    }

    private fun filterByUserExercises(exercises: List<ExerciseItem>): List<ExerciseItem> {

        val filteredExercises = mutableListOf<ExerciseItem>()

        for (exercise in exercises) {

            if (exercise.exercise is Exercises.UserExercise) {

                filteredExercises.add(exercise)

            }

        }

        return filteredExercises

    }


    private fun filterExercisesByName(
        name: String,
        exercises: List<ExerciseItem>
    ): List<ExerciseItem> {

        val filteredExercises = mutableListOf<ExerciseItem>()

        for (exercise in exercises) {

            if (exercise.exercise.name.lowercase().contains(name.lowercase())) {

                filteredExercises.add(exercise)

            }

        }

        return filteredExercises

    }

    private fun filterExercisesByEquipment(
        equipment : Equipment,
        exercises: List<ExerciseItem>
    ): List<ExerciseItem> {

        val filteredExercises = mutableListOf<ExerciseItem>()

        for (exercise in exercises) {

            if (exercise.exercise.equipment == equipment) {

                filteredExercises.add(exercise)

            }

        }

        return filteredExercises

    }

    private fun filterExercisesByMuscularGroup(
        muscularGroup : MuscularGroup,
        exercises: List<ExerciseItem>
    ): List<ExerciseItem> {


        val filteredExercises = mutableListOf<ExerciseItem>()

        for (exercise in exercises) {

            if (exercise.exercise.muscularGroup == muscularGroup) {

                filteredExercises.add(exercise)

            }

        }

        return filteredExercises


    }


}