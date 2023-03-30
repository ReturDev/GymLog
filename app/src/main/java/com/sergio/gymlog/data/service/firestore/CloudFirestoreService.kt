package com.sergio.gymlog.data.service.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
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

        //TODO linea solo para pruebas
        user.dailyTraining = UserInfo.DailyTraining(date = Date(), training = Training("Training", exercises = emptyList()))

        db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(user.id)
            .set(user).await()

    }

    override suspend fun getUserInfo(userUID: String): Task<DocumentSnapshot> {
        return  db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(userUID).get()
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

    override suspend fun getProvidedExercises() : Task<QuerySnapshot> {

        return db.collection(CloudFirestoreConstants.EXERCISES_COLLECTION_TAG).get()

    }

    override suspend fun getUserExercises(userUID: String): Task<QuerySnapshot> {

        return db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(userUID).collection(CloudFirestoreConstants.USER_EXERCISES_COLLECTION_TAG).get()
    }

}