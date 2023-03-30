package com.sergio.gymlog.data.service.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.sergio.gymlog.data.model.Exercises
import com.sergio.gymlog.data.model.UserInfo

interface CloudFirestore {

    suspend fun createNewUser(user : UserInfo)

    suspend fun getUserInfo(userUID: String): Task<DocumentSnapshot>

    suspend fun existUser(userUID : String) : Boolean

    suspend fun updateDailyTraining(userUID: String, training: UserInfo.DailyTraining?)

    suspend fun getProvidedExercises() : Task<QuerySnapshot>

    suspend fun getUserExercises(userUID: String): Task<QuerySnapshot>


}