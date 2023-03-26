package com.sergio.gymlog.data.firestore

import com.sergio.gymlog.data.model.Exercise
import com.sergio.gymlog.data.model.Training
import com.sergio.gymlog.data.model.User
import com.sergio.gymlog.data.model.FirebaseResource
import java.net.URI
import javax.inject.Inject

interface CloudFirestore {

    suspend fun createNewUser(user : User): FirebaseResource<Void>

    suspend fun getUserInfo(userUID: String): FirebaseResource<Boolean>

    suspend fun existUser(userUID : String) : Boolean

    suspend fun updateUserName(username : String)

    suspend fun updateUserPhoto(photo : URI)

    suspend fun updateUserWeight(weight : Int)

    suspend fun updateDailyTraining(training : Training)

    suspend fun setExercise(exercise : Exercise)

    suspend fun setRecord(record : Training)

    suspend fun setTraining(training : Training)



}