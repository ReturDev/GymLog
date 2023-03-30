package com.sergio.gymlog.data.repository.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.toObject
import com.sergio.gymlog.data.model.UserInfo
import com.sergio.gymlog.data.model.ApplicationData
import com.sergio.gymlog.data.service.firestore.CloudFirestoreService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepository @Inject constructor(
    private val auth : FirebaseAuth,
    private val cloudFirestoreService: CloudFirestoreService,
    private val applicationData: ApplicationData

    ) {

    suspend fun manageUserData(){

        val currentUserData = auth.currentUser
        val userInfo : UserInfo


        if (cloudFirestoreService.existUser(currentUserData!!.uid)){

            userInfo = getUserData(currentUserData.uid)

        }else{

             userInfo = UserInfo(
                id = currentUserData.uid,
                email = currentUserData.email!!,
                username = currentUserData.displayName ?: "",
                photo = currentUserData.photoUrl.toString()
            )

            createUser(userInfo)

        }

        applicationData.userInfo.setAllData(userInfo)

    }


    private suspend fun getUserData(userUID : String) : UserInfo{

        val data = cloudFirestoreService.getUserInfo(userUID).await()
        return data.toObject()!!

    }

    private suspend fun createUser(currentUserData : UserInfo){
        cloudFirestoreService.createNewUser(currentUserData)
    }

     suspend fun removeDailyTraining(){

         applicationData.userInfo.dailyTraining = null
         cloudFirestoreService.updateDailyTraining(applicationData.userInfo.id, null)

    }

    suspend fun setDailyTraining(dailyTraining: UserInfo.DailyTraining){

        applicationData.userInfo.dailyTraining = dailyTraining
        cloudFirestoreService.updateDailyTraining(applicationData.userInfo.id, applicationData.userInfo.dailyTraining)

    }

}