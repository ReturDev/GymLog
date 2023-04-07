package com.sergio.gymlog.data.remote.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.sergio.gymlog.data.model.remote.firestore.TrainingCloud
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.data.model.user.UserInfo

interface CloudFirestore {

    suspend fun createNewUser(user : UserInfo)

    suspend fun getUserInfo(userUID: String): DocumentSnapshot?

    suspend fun updateDailyTraining(userUID: String, training: UserInfo.DailyTraining?)

    suspend fun getProvidedExercises() : QuerySnapshot?

    suspend fun getUserExercises(userUID: String): QuerySnapshot?

    suspend fun getUserTrainings(userUID: String): QuerySnapshot?
    suspend fun createUserTraining(userID: String, training: TrainingCloud)
    suspend fun deleteUserTraining(userUID: String, trainingId: String)
    suspend fun getUserExerciseReference(userID: String, exerciseID: String): DocumentReference
    suspend fun getExerciseReference(exerciseID: String): DocumentReference
    suspend fun generateTrainingRandomId(userID: String): String

    suspend fun setUserTraining(userID: String, training: TrainingCloud)
}