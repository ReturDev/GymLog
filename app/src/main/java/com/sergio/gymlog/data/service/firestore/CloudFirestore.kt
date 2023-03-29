package com.sergio.gymlog.data.service.firestore

import com.sergio.gymlog.data.model.Exercises
import com.sergio.gymlog.data.model.UserInfo

interface CloudFirestore {

    suspend fun createNewUser(user : UserInfo)

    suspend fun getUserInfo(userUID: String): UserInfo

    suspend fun existUser(userUID : String) : Boolean

    suspend fun updateDailyTraining(userUID: String, training: UserInfo.DailyTraining?)

    suspend fun getProvidedExercises() : List<Exercises.ProvidedExercise>

    suspend fun getUserExercises(userUID: String): List<Exercises.UserExercise>


}