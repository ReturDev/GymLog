package com.sergio.gymlog.data.remote.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.remote.firestore.ReferencedExercises
import com.sergio.gymlog.data.model.remote.firestore.TrainingCloud
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.data.model.user.UserInfo

interface CloudFirestore {

    suspend fun createNewUser(user : UserInfo)

    suspend fun getUserInfo(userID: String): DocumentSnapshot?

    suspend fun <T> modifyUserInfo(userID: String, fieldName : String, fieldData : T)

    suspend fun updateDailyTraining(userID: String, training: UserInfo.DailyTraining?)

    suspend fun getProvidedExercises() : QuerySnapshot?

    suspend fun getUserExercises(userID: String): QuerySnapshot?

    suspend fun getUserTrainings(userID: String): QuerySnapshot?
    suspend fun createUserTraining(userID: String, training: TrainingCloud)
    suspend fun deleteUserTraining(userID: String, trainingID: String)
    suspend fun getUserExerciseReference(userID: String, userExerciseID: String): DocumentReference
    suspend fun getExerciseReference(exerciseID: String): DocumentReference
    suspend fun generateTrainingRandomId(userID: String): String

    suspend fun setUserTraining(userID: String, training: TrainingCloud)
    suspend fun createUserExercise(userID: String, exercise: Exercises.UserExercise)
    suspend fun generateUserExerciseRandomId(userID: String): String
    suspend fun deleteUserExercise(
        userID: String,
        exerciseReference: DocumentReference,
        trainingsIds: Map<String, List<ReferencedExercises>>
    )
}