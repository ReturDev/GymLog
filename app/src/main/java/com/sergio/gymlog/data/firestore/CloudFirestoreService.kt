package com.sergio.gymlog.data.firestore

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sergio.gymlog.Exercise
import com.sergio.gymlog.Training
import com.sergio.gymlog.User
import com.sergio.gymlog.data.model.FirebaseResource
import com.sergio.gymlog.util.helper.CloudFirestoreConstants
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.tasks.await
import java.io.File
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudFirestoreService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db : FirebaseFirestore
) : CloudFirestore {
    override suspend fun createNewUser(user: User): FirebaseResource<Void> {

        val userMap = hashMapOf(
            CloudFirestoreConstants.USERNAME_TAG to user.username,
            CloudFirestoreConstants.EMAIL_TAG to user.email,
            CloudFirestoreConstants.PHOTO_URL_TAG to user.photo,
            CloudFirestoreConstants.WEIGHT_TAG to user.weight,
            CloudFirestoreConstants.DAILY_TRAINING_TAG to user.dailyTraining,
            CloudFirestoreConstants.REPETITIONS_TAG to user.repetitions,
            CloudFirestoreConstants.SETS_TAG to user.sets
        )

        return try {

            val res = db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(user.id)
                .set(userMap).await()

            FirebaseResource.Success(res)

        }catch (e : Exception){

            FirebaseResource.Failure(e)

        }
    }

    override suspend fun getUserInfo(userUID: String): FirebaseResource<User> {
        return try {

            val data = db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(userUID).get().await().data

            val user = data!!.let { d -> User(
                id = userUID,
                username = d[CloudFirestoreConstants.USERNAME_TAG].toString(),
                email = d[CloudFirestoreConstants.EMAIL_TAG].toString(),
                photo = Uri.fromFile(File(d[CloudFirestoreConstants.PHOTO_URL_TAG].toString()))
                )
            }

            FirebaseResource.Success(user)

        }catch (e : Exception){

            FirebaseResource.Failure(e)

        }

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

    override suspend fun updateDailyTraining(training: Training) {

    }

    override suspend fun existDailyTraining(): Boolean {
        return false
    }

    override suspend fun setExercise(exercise: Exercise) {

    }

    override suspend fun setRecord(record: Training) {

    }

    override suspend fun setTraining(training: Training) {

    }

}