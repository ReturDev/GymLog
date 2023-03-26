package com.sergio.gymlog.data.repository.firestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.sergio.gymlog.data.model.Exercise
import com.sergio.gymlog.data.model.Training
import com.sergio.gymlog.data.model.User
import com.sergio.gymlog.data.model.FirebaseResource
import com.sergio.gymlog.util.helper.CloudFirestoreConstants
import kotlinx.coroutines.tasks.await
import java.net.URI
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudFirestoreService @Inject constructor(
    private val db : FirebaseFirestore,
) : CloudFirestore {
    override suspend fun createNewUser(user: User){

        user.dailyTraining = User.DailyTraining(date = Date(), training = Training("Training", exercises = emptyList()))

        db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(user.id)
            .set(user).await()

    }

    override suspend fun getUserInfo(userUID: String) : User{
        val data = db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(userUID).get().await()
        return data.toObject()!!
    }

    override suspend fun existUser(userUID: String): Boolean {
        val data = db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(userUID).get().await().data
        return data != null
    }

    override suspend fun updateUserName(username: String) {

    }

    override suspend fun updateUserPhoto(photo: URI) {
    }

    override suspend fun updateUserWeight(weight: Int) {

    }

    override suspend fun updateDailyTraining(userUID: String, training: User.DailyTraining?) {

        db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(userUID).update(
            mapOf(
                    CloudFirestoreConstants.DAILY_TRAINING_TAG to training
            )
        )


    }

    override suspend fun setExercise(exercise: Exercise) {

    }

    override suspend fun setRecord(record: Training) {

    }

    override suspend fun setTraining(training: Training) {

    }

}