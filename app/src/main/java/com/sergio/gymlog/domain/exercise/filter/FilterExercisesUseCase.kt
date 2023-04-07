package com.sergio.gymlog.domain.exercise.filter

import com.sergio.gymlog.data.model.exercise.Equipment
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.MuscularGroup
import com.sergio.gymlog.domain.exercise.GetAllExercisesUseCase
import javax.inject.Inject

class FilterExercisesUseCase @Inject constructor(

    private val getAllExercisesUseCase: GetAllExercisesUseCase

) {

    suspend operator fun invoke(name : String, userExercises : Boolean, equipments : List<Equipment>, muscularGroups : List<MuscularGroup> ) : List<Exercises>{

        var exercises = getAllExercisesUseCase()

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

    private fun filterByUserExercises(exercises: List<Exercises>): List<Exercises> {

        val filteredExercises = mutableListOf<Exercises>()

        for (exercise in exercises){

            if (exercise is Exercises.UserExercise){

                filteredExercises.add(exercise)

            }

        }

        return filteredExercises

    }


    private fun filterExercisesByName(name : String, exercises: List<Exercises>) : List<Exercises>{

        val filteredExercises = mutableListOf<Exercises>()

        for (exercise in exercises){

            if (exercise.name.contains(name)){

                filteredExercises.add(exercise)

            }

        }

        return filteredExercises

    }

    private fun filterExercisesByEquipment(equipments : List<Equipment>, exercises: List<Exercises>) : List<Exercises>{

        val filteredExercises = mutableListOf<Exercises>()

        for (exercise in exercises){

            for (equipment in equipments){

                if (exercise.equipment  == equipment){

                    filteredExercises.add(exercise)

                }

            }

        }

        return filteredExercises

    }

    private fun filterExercisesByMuscularGroup(muscularGroups: List<MuscularGroup>, exercises: List<Exercises>) : List<Exercises>{


        val filteredExercises = mutableListOf<Exercises>()

        for (exercise in exercises){

            for (muscularG in muscularGroups){

                if (exercise.muscularGroup  == muscularG){

                    filteredExercises.add(exercise)

                }

            }

        }

        return filteredExercises


    }


}