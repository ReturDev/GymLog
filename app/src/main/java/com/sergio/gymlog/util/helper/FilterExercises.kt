package com.sergio.gymlog.util.helper

import com.sergio.gymlog.data.model.exercise.Equipment
import com.sergio.gymlog.data.model.exercise.ExerciseItem
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.MuscularGroup

object FilterExercises {

      fun filter(name : String = "", userExercises : Boolean = false, equipments : List<Equipment> = emptyList(), muscularGroups : List<MuscularGroup> = emptyList(), exercises : List<ExerciseItem>) : List<ExerciseItem> {

        var filteredExercises = exercises

        if (name.isNotBlank()){

            filteredExercises = filterExercisesByName(name, exercises)

        }else{



        }

        if (userExercises){


            filteredExercises = filterByUserExercises(exercises)

        }

        if (equipments.isNotEmpty()){

            filteredExercises = filterExercisesByEquipment(equipments,exercises)

        }

        if (equipments.isNotEmpty()){

            filteredExercises = filterExercisesByMuscularGroup(muscularGroups, exercises)

        }

        return filteredExercises

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