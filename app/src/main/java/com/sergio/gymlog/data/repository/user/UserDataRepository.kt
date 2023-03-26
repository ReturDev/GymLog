package com.sergio.gymlog.data.repository.user

import android.util.Log
import com.sergio.gymlog.data.model.User
import com.sergio.gymlog.data.model.UserFirestoreData
import com.sergio.gymlog.data.repository.authentication.FirebaseAuthenticationService
import com.sergio.gymlog.data.repository.firestore.CloudFirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepository @Inject constructor(

    private val firebaseAuthenticationService: FirebaseAuthenticationService,
    private val cloudFirestoreService: CloudFirestoreService

) {

    private val _userDataState = MutableStateFlow(UserFirestoreData())
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

         val userData = cloudFirestoreService.getUserInfo(userUID)
        userData.id = userUID

        _userDataState.update { currentState ->

            currentState.copy(

                userData = userData

            )
        }

    }

    private suspend fun createUserData(currentUserData : User){

        cloudFirestoreService.createNewUser(currentUserData)

        _userDataState.update { currentState ->

            currentState.copy(

                userData = currentUserData

            )

        }

    }

     suspend fun removeDailyTraining(){

        _userDataState.update { currentState ->

            val userData = currentState.userData
            userData?.let {

                userData.dailyTraining = User.DailyTraining(Date())

                Log.e("Datos", currentState.userData.id)
                cloudFirestoreService.updateDailyTraining(currentState.userData.id, userData.dailyTraining)
            }

            currentState.copy(
                userData = userData
            )

        }



    }

}