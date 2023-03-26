package com.sergio.gymlog.data.firestore

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.sergio.gymlog.data.model.Exercise
import com.sergio.gymlog.data.model.Training
import com.sergio.gymlog.data.model.User
import com.sergio.gymlog.data.model.FirebaseResource
import com.sergio.gymlog.util.helper.CloudFirestoreConstants
import kotlinx.coroutines.tasks.await
import java.io.File
import java.net.URI
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudFirestoreService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db : FirebaseFirestore,
    private val userData : User
) : CloudFirestore {
    override suspend fun createNewUser(user: User): FirebaseResource<Void> {

        user.dailyTraining = User.DailyTraining(date = Date(), training = Training("Training", exercises = emptyList()))

//        val userMap = hashMapOf(
//            CloudFirestoreConstants.USERNAME_TAG to user.username,
//            CloudFirestoreConstants.EMAIL_TAG to user.email,
//            CloudFirestoreConstants.PHOTO_URL_TAG to user.photo,
//            CloudFirestoreConstants.WEIGHT_TAG to user.weight,
//            CloudFirestoreConstants.DAILY_TRAINING_TAG to user.dailyTraining,
//            CloudFirestoreConstants.REPETITIONS_TAG to user.repetitions,
//            CloudFirestoreConstants.SETS_TAG to user.sets
//        )

        return try {

            val res = db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(user.id)
                .set(user).await()

            FirebaseResource.Success(res)

        }catch (e : Exception){

            FirebaseResource.Failure(e)

        }
    }

    override suspend fun getUserInfo(userUID: String): FirebaseResource<Boolean> {
        return try {

            val data = db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(userUID).get().await()
            data!!.let {d ->  val user = d.toObject<User>()!!

                userData.apply {
                    this.id = userUID
                    this.username= user.username
                    this.email = user.email
                    this.photo = user.photo
                    this.weight = user.weight
                    this.dailyTraining = user.dailyTraining
                    this.repetitions = user.repetitions
                    this.sets = user.sets

                }
            }

            FirebaseResource.Success(true)

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

    override suspend fun setExercise(exercise: Exercise) {

    }

    override suspend fun setRecord(record: Training) {

    }

    override suspend fun setTraining(training: Training) {

    }

}