package com.sergio.gymlog.domain.user

import com.google.firebase.auth.FirebaseAuth
import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.data.repository.user.UserDataRepository
import javax.inject.Inject

class ManageUserInfoUseCase @Inject constructor(
    private val applicationData: ApplicationData,
    private val auth : FirebaseAuth,
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke(){

        val currentUserData = auth.currentUser
        var userInfo = userDataRepository.getUserData(currentUserData!!.uid)

        if (userInfo == null){
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