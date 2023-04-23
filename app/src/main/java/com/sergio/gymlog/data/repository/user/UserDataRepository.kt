package com.sergio.gymlog.data.repository.user

import com.google.firebase.firestore.ktx.toObject
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.data.remote.firestore.CloudFirestoreService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepository @Inject constructor(
    private val cloudFirestoreService: CloudFirestoreService
) {

    suspend fun getUserData(userID : String) : UserInfo?{

        val data = cloudFirestoreService.getUserInfo(userID)
        return data?.toObject()

    }

    suspend fun createUser(currentUserData : UserInfo){
        cloudFirestoreService.createNewUser(currentUserData)
    }

    suspend fun removeDailyTraining(userID: String){
         cloudFirestoreService.updateDailyTraining(userID, null)
    }

    suspend fun setDailyTraining(userID: String, dailyTraining: UserInfo.DailyTraining){

        cloudFirestoreService.updateDailyTraining(userID, dailyTraining)

    }

    suspend fun <T> modifyUserInfo(userID : String, fieldName : String, fieldData : T){

        cloudFirestoreService.modifyUserInfo(userID, fieldName, fieldData)

    }


}