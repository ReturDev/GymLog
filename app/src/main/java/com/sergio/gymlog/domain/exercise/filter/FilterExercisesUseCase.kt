package com.sergio.gymlog.domain.exercise.filter

import com.sergio.gymlog.data.model.exercise.Equipment
import com.sergio.gymlog.data.model.exercise.ExerciseItem
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.MuscularGroup
import com.sergio.gymlog.domain.exercise.GetAllExercisesUseCase
import com.sergio.gymlog.domain.exercise.GetExercisesAsExerciseItemsUseCase
import javax.inject.Inject

class FilterExercisesUseCase @Inject constructor(

    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    private val getExercisesAsExerciseItemsUseCase: GetExercisesAsExerciseItemsUseCase

) {

    suspend operator fun invoke(name : String = "", userExercises : Boolean = false, equipments : List<Equipment> = emptyList(), muscularGroups : List<MuscularGroup> = emptyList()) : List<ExerciseItem> {

        val exercisesIds = getAllExercisesUseCase().map { ex -> ex.id }.toTypedArray()
        var exercises = getExercisesAsExerciseItemsUseCase(exercisesIds).toList()

        if (name.isNotBlank()){

           exercises = filterExercisesByName(name, exercises)

        }

        if (userExercises){


            exercises = filterByUserExercises(exercises)

        }

        if (equipments.isNotEmpty()){

            exercises = filterExercisesByEquipment(equipments,exercises)

        }

        if (equipments.isNotEmpty()){

            exercises = filterExercisesByMuscularGroup(muscularGroups, exercises)

        }

        return exercises

    }

    private fun filterByUserExercises(exercises: List<ExerciseItem>): List<ExerciseItem> {

        val filteredExercises = mutableListOf<ExerciseItem>()

        for (exercise in exercises){

            if (exercise.exercise is Exercises.UserExercise){

                filteredExercises.add(exercise)

            }

        }

        return filteredExercises

    }


    private fun filterExercisesByName(name : String, exercises: List<ExerciseItem>) : List<ExerciseItem>{

        val filteredExercises = mutableListOf<ExerciseItem>()

        for (exercise in exercises){

            if (exercise.exercise.name.contains(name)){

                filteredExercises.add(exercise)

            }

        }

        return filteredExercises

    }

    private fun filterExercisesByEquipment(equipments : List<Equipment>, exercises: List<ExerciseItem>) : List<ExerciseItem>{

        val filteredExercises = mutableListOf<ExerciseItem>()

        for (exercise in exercises){

            for (equipment in equipments){

                if (exercise.exercise.equipment  == equipment){

                    filteredExercises.add(exercise)

                }

            }

        }

        return filteredExercises

    }

    private fun filterExercisesByMuscularGroup(muscularGroups: List<MuscularGroup>, exercises: List<ExerciseItem>) : List<ExerciseItem>{


        val filteredExercises = mutableListOf<ExerciseItem>()

        for (exercise in exercises){

            for (muscularG in muscularGroups){

                if (exercise.exercise.muscularGroup  == muscularG){

                    filteredExercises.add(exercise)

                }

            }

        }

        return filteredExercises


    }


}