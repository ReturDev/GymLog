package com.sergio.gymlog.data.repository.user

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObject
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.remote.firestore.ReferencedExercises
import com.sergio.gymlog.data.remote.firestore.CloudFirestoreService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ExercisesRepository @Inject constructor(
    private val cloudFirestoreService: CloudFirestoreService
){
     suspend fun getProvidedExercises() : MutableList<Exercises.ProvidedExercise> {

         val documents = cloudFirestoreService.getProvidedExercises()!!.documents

         val exercisesList = mutableListOf<Exercises.ProvidedExercise>()

         for (doc in documents){

             val exercise = doc.toObject<Exercises.ProvidedExercise>()!!
             exercise.id = doc.id


             exercisesList.add(exercise)

         }

         return exercisesList
    }

    suspend fun getUserExercises(uid : String) : MutableList<Exercises.UserExercise>{

        val documents = cloudFirestoreService.getUserExercises(uid)?.documents

        val exercisesList = mutableListOf<Exercises.UserExercise>()

        documents?.let {


            for (doc in documents){

                val exercise = doc.toObject<Exercises.UserExercise>()!!
                exercise.id = doc.id

                exercisesList.add(exercise)

            }

        }

        return exercisesList

    }

    suspend fun createUserExercise(uid : String, exercise : Exercises.UserExercise){

        cloudFirestoreService.createUserExercise(uid, exercise)

    }

    suspend fun deleteUserExercise(userID: String, exerciseReference: DocumentReference, trainingsIds : Map<String, List<ReferencedExercises>> ){

        cloudFirestoreService.deleteUserExercise(userID, exerciseReference, trainingsIds)

    }

    suspend fun getUserExerciseReference(userID: String, userExerciseID : String): DocumentReference {
        return cloudFirestoreService.getUserExerciseReference(userID, userExerciseID)
    }

    suspend fun generateUserExerciseRandomId(userID : String) : String{
        return cloudFirestoreService.generateUserExerciseRandomId(userID)
    }

    suspend fun getExerciseReference(exerciseID : String): DocumentReference {
        return cloudFirestoreService.getExerciseReference(exerciseID)
    }

}