package com.sergio.gymlog.data.repository.user

import com.sergio.gymlog.data.model.UserInfo
import com.sergio.gymlog.data.model.ApplicationData
import com.sergio.gymlog.data.model.Exercises
import com.sergio.gymlog.data.model.Training
import com.sergio.gymlog.data.service.authentication.FirebaseAuthenticationService
import com.sergio.gymlog.data.service.firestore.CloudFirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepository @Inject constructor(

    private val firebaseAuthenticationService: FirebaseAuthenticationService,
    private val cloudFirestoreService: CloudFirestoreService,
    private val applicationData: ApplicationData

    ) {

    suspend fun manageUserData(){

        val currentUserData = firebaseAuthenticationService.getUserData()

        if (cloudFirestoreService.existUser(currentUserData.id)){

            getUserData(currentUserData.id)

        }else{

            createUserData(currentUserData)

        }
    }


    private suspend fun getUserData(userUID : String){

        val tempUser = cloudFirestoreService.getUserInfo(userUID)
        tempUser.id = userUID

        applicationData.userInfo.setAllData(tempUser)

    }

    private suspend fun createUserData(currentUserData : UserInfo){

        cloudFirestoreService.createNewUser(currentUserData)

        applicationData.userInfo.setAllData(currentUserData)

    }

     suspend fun removeDailyTraining(){

         applicationData.userInfo.dailyTraining = null
         cloudFirestoreService.updateDailyTraining(applicationData.userInfo.id, null)

    }

    suspend fun setDailyTraining(dailyTraining: UserInfo.DailyTraining){

        applicationData.userInfo.dailyTraining = dailyTraining
        cloudFirestoreService.updateDailyTraining(applicationData.userInfo.id, applicationData.userInfo.dailyTraining)

    }



     suspend fun addUserExercise(){


    }

    suspend fun removeUserExercise(){

    }


}