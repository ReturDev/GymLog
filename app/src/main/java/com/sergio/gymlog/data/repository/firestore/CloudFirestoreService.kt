package com.sergio.gymlog.data.repository.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.sergio.gymlog.data.model.Exercises
import com.sergio.gymlog.data.model.Training
import com.sergio.gymlog.data.model.UserInfo
import com.sergio.gymlog.util.CloudFirestoreConstants
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudFirestoreService @Inject constructor(
    private val db : FirebaseFirestore,
) : CloudFirestore {
    override suspend fun createNewUser(user: UserInfo){

        user.dailyTraining = UserInfo.DailyTraining(date = Date(), training = Training("Training", exercises = emptyList()))

        db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(user.id)
            .set(user).await()

    }

    override suspend fun getUserInfo(userUID: String) : UserInfo{
        val data = db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(userUID).get().await()
        return data.toObject()!!
    }

    override suspend fun existUser(userUID: String): Boolean {
        val data = db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(userUID).get().await().data
        return data != null
    }

    override suspend fun updateDailyTraining(userUID: String, training: UserInfo.DailyTraining?) {

        db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(userUID).update(
            mapOf(
                    CloudFirestoreConstants.DAILY_TRAINING_TAG to training
            )
        )

    }

    override suspend fun getProvidedExercises() : List<Exercises.ProvidedExercise> {

        val documents = db.collection(CloudFirestoreConstants.EXERCISES_COLLECTION_TAG).get().await().documents

        val exercisesList = mutableListOf<Exercises.ProvidedExercise>()

        for (doc in documents){

            val exercise = doc.toObject<Exercises.ProvidedExercise>()!!
            exercise.id = doc.id


            exercisesList.add(exercise)

        }

        return exercisesList.toList()

    }

    override suspend fun getUserExercises(userUID: String): List<Exercises.UserExercise> {

        val documents = db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(userUID).collection(CloudFirestoreConstants.USER_EXERCISES_COLLECTION_TAG).get().await().documents

        val exercisesList = mutableListOf<Exercises.UserExercise>()

        for (doc in documents){

            val exercise = doc.toObject<Exercises.UserExercise>()!!
            exercise.id = doc.id

            exercisesList.add(exercise)

        }

        return exercisesList.toList()

    }

}