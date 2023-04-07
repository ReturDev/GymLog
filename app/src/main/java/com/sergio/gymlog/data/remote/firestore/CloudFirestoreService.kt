package com.sergio.gymlog.data.remote.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.sergio.gymlog.data.model.remote.firestore.TrainingCloud
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.data.model.user.UserInfo
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

    override suspend fun getUserInfo(userID: String): DocumentSnapshot? {
        return  db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(userID).get().await()
    }

    override suspend fun updateDailyTraining(userID: String, training: UserInfo.DailyTraining?) {

        db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG).document(userID).update(
            mapOf(
                    CloudFirestoreConstants.DAILY_TRAINING_TAG to training
            )
        )

    }

    override suspend fun getProvidedExercises() : QuerySnapshot? {

        return db.collection(CloudFirestoreConstants.EXERCISES_COLLECTION_TAG).get().await()

    }

    override suspend fun getUserExercises(userID: String) : QuerySnapshot? {

        return db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG)
            .document(userID)
            .collection(CloudFirestoreConstants.USER_EXERCISES_COLLECTION_TAG)
            .get()
            .await()

    }

    suspend fun deleteUserExercise(userID: String, exerciseID: String){
        db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG)
            .document(userID)
            .collection(CloudFirestoreConstants.USER_EXERCISES_COLLECTION_TAG)
            .document(exerciseID)
            .delete()
    }



    override suspend fun getUserTrainings(userID: String) : QuerySnapshot? {

        return db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG)
            .document(userID)
            .collection(CloudFirestoreConstants.TRAINING_COLLECTION_TAG)
            .get()
            .await()

    }

    override suspend fun createUserTraining(userID: String, training: TrainingCloud){

        db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG)
            .document(userID)
            .collection(CloudFirestoreConstants.TRAINING_COLLECTION_TAG)
            .add(training)

    }

    override suspend fun deleteUserTraining(userID: String, trainingID : String){

        db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG)
            .document(userID)
            .collection(CloudFirestoreConstants.TRAINING_COLLECTION_TAG)
            .document(trainingID)
            .delete()

    }

    override suspend fun setUserTraining(userID : String, training : TrainingCloud){

        db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG)
            .document(userID)
            .collection(CloudFirestoreConstants.TRAINING_COLLECTION_TAG)
            .document(training.id).set(training)

    }

    override suspend fun generateTrainingRandomId(userID: String) : String{

        val collectionRef = db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG)
            .document(userID)
            .collection(CloudFirestoreConstants.TRAINING_COLLECTION_TAG)

        return collectionRef.document().id

    }

    override suspend fun getUserExerciseReference(userID: String, userExerciseID : String): DocumentReference {

        return db.collection(CloudFirestoreConstants.USER_COLLECTION_TAG)
            .document(userID)
            .collection(CloudFirestoreConstants.USER_EXERCISES_COLLECTION_TAG)
            .document(userExerciseID)

    }

    override suspend fun getExerciseReference(exerciseID : String): DocumentReference {

        return db.collection(CloudFirestoreConstants.EXERCISES_COLLECTION_TAG)
            .document(exerciseID)

    }

}