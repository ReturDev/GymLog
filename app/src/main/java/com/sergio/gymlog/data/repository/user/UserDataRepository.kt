package com.sergio.gymlog.data.repository.user

import com.google.firebase.firestore.ktx.toObject
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.data.model.remote.firestore.TrainingCloud
import com.sergio.gymlog.data.remote.firestore.CloudFirestoreService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepository @Inject constructor(
    private val cloudFirestoreService: CloudFirestoreService
) {

    suspend fun getUserData(userUID : String) : UserInfo?{

        val data = cloudFirestoreService.getUserInfo(userUID)
        return data?.toObject()

    }

    suspend fun createUser(currentUserData : UserInfo){
        cloudFirestoreService.createNewUser(currentUserData)
    }

    suspend fun removeDailyTraining(userUID: String){
         cloudFirestoreService.updateDailyTraining(userUID, null)
    }

    suspend fun setDailyTraining(userUID: String, dailyTraining: UserInfo.DailyTraining){

        cloudFirestoreService.updateDailyTraining(userUID, dailyTraining)

    }


}