package com.sergio.gymlog.domain.user

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.data.repository.user.UserDataRepository
import javax.inject.Inject

class ManageUserInfoUseCase @Inject constructor(
    private val applicationData: ApplicationData,
    private val auth : FirebaseAuth,
    private val userDataRepository: UserDataRepository,
    private val getUserInfoUseCase: GetUserInfoUseCase
) {

    suspend operator fun invoke(){

        val currentUserData = auth.currentUser!!
        var userInfo = getUserInfoUseCase()
        Log.e("FFF", "UserObtenido")
        if (userInfo.id.isEmpty()){
            userInfo = UserInfo(
                id = currentUserData.uid,
                email = currentUserData.email!!,
                username = currentUserData.displayName ?: "",
                photo = currentUserData.photoUrl.toString()
            )



            userDataRepository.createUser(userInfo)

        }

        applicationData.userInfo.setAllData(userInfo)

    }

}