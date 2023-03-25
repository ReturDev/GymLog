package com.sergio.gymlog.data.firestore

import com.sergio.gymlog.Exercise
import com.sergio.gymlog.Training
import com.sergio.gymlog.User
import com.sergio.gymlog.data.model.FirebaseResource
import java.net.URI

interface CloudFirestore {

    suspend fun createNewUser(user : User): FirebaseResource<Void>

    suspend fun getUserInfo(userUID : String) : FirebaseResource<User>

    suspend fun existUser(userUID : String) : Boolean

    suspend fun updateUserName(username : String)

    suspend fun updateUserPhoto(photo : URI)

    suspend fun updateUserWeight(weight : Int)

    suspend fun updateDailyTraining(training : Training)

    suspend fun existDailyTraining() : Boolean

    suspend fun setExercise(exercise : Exercise)

    suspend fun setRecord(record : Training)

    suspend fun setTraining(training : Training)


}