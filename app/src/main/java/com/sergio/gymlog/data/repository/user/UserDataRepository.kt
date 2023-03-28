package com.sergio.gymlog.data.repository.user

import com.sergio.gymlog.data.model.UserInfo
import com.sergio.gymlog.data.model.ApplicationData
import com.sergio.gymlog.data.repository.authentication.FirebaseAuthenticationService
import com.sergio.gymlog.data.repository.firestore.CloudFirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepository @Inject constructor(

    private val firebaseAuthenticationService: FirebaseAuthenticationService,
    private val cloudFirestoreService: CloudFirestoreService,
    applicationData: ApplicationData,

) {

    private val _userDataState = MutableStateFlow(applicationData)
    val userDataState = _userDataState.asStateFlow()

    suspend fun manageUserData(){

        val currentUserData = firebaseAuthenticationService.getUserData()

        if (cloudFirestoreService.existUser(currentUserData.id)){

            getUserData(currentUserData.id)

        }else{

            createUserData(currentUserData)

        }
    }


    private suspend fun getUserData(userUID : String){

         val userInfo = cloudFirestoreService.getUserInfo(userUID)
        userInfo.id = userUID

        _userDataState.update { currentState ->

            currentState.copy(

                userInfo = userInfo

            )
        }

    }

    private suspend fun createUserData(currentUserData : UserInfo){

        cloudFirestoreService.createNewUser(currentUserData)

        _userDataState.update { currentState ->

            currentState.copy(

                userInfo = currentUserData

            )

        }

    }

     suspend fun removeDailyTraining(){

        _userDataState.update { currentState ->

            val userInfo = currentState.userInfo
            userInfo?.let {

                userInfo.dailyTraining = null
                cloudFirestoreService.updateDailyTraining(currentState.userInfo.id, userInfo.dailyTraining)
            }

            currentState.copy(
                userInfo = userInfo
            )

        }

    }

    suspend fun setDailyTraining(dailyTraining: UserInfo.DailyTraining){

        _userDataState.update { currentState ->

            val userInfo = currentState.userInfo
            userInfo?.let {

                userInfo.dailyTraining = dailyTraining
                cloudFirestoreService.updateDailyTraining(currentState.userInfo.id, userInfo.dailyTraining)
            }

            currentState.copy(
                userInfo = userInfo
            )

        }

    }

    private suspend fun getExercises(){

        _userDataState.update { currentState ->

            val providedExercises = cloudFirestoreService.getProvidedExercises()
            val userExercises = cloudFirestoreService.getUserExercises(currentState.userInfo!!.id)

            currentState.copy(

                providedExercises = providedExercises,
                userExercises = userExercises

            )

        }

    }

     suspend fun addUserExercise(){


    }

    suspend fun removeUserExercise(){

    }


}