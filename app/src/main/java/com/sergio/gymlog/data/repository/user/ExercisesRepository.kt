package com.sergio.gymlog.data.repository.user

import android.util.Log
import com.google.firebase.firestore.ktx.toObject
import com.sergio.gymlog.data.model.ApplicationData
import com.sergio.gymlog.data.model.Exercises
import com.sergio.gymlog.data.service.firestore.CloudFirestoreService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ExercisesRepository @Inject constructor(
    private val cloudFirestoreService: CloudFirestoreService,
    private val applicationData: ApplicationData
){
     suspend fun getProvidedExercises(){

         if (applicationData.providedExercise.isEmpty()){

             val documents = cloudFirestoreService.getProvidedExercises().await().documents

             val exercisesList = mutableListOf<Exercises.ProvidedExercise>()

             for (doc in documents){

                 val exercise = doc.toObject<Exercises.ProvidedExercise>()!!
                 exercise.id = doc.id


                 exercisesList.add(exercise)

             }

             applicationData.providedExercise.addAll(exercisesList)

         }

    }

    suspend fun getUserExercises(){

        if (applicationData.userExercises.isEmpty()){

            val task = cloudFirestoreService.getUserExercises(applicationData.userInfo.id)

            if(task.isSuccessful){
                val documents = task.result.documents

                val exercisesList = mutableListOf<Exercises.UserExercise>()

                for (doc in documents){

                    val exercise = doc.toObject<Exercises.UserExercise>()!!
                    exercise.id = doc.id

                    exercisesList.add(exercise)

                }

                applicationData.userExercises.addAll(exercisesList)

            }
        }

    }


}