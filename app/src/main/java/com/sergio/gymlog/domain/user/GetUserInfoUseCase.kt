package com.sergio.gymlog.domain.user

import com.google.firebase.auth.FirebaseAuth
import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.data.repository.user.UserDataRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val applicationData: ApplicationData,
    private val userDataRepository: UserDataRepository,
    private val auth : FirebaseAuth
) {

    suspend operator fun invoke(): UserInfo {

        if (applicationData.userInfo.id.isEmpty()){

            val userInfo = userDataRepository.getUserData(auth.uid!!)
            userInfo?.let { info ->
                applicationData.userInfo.setAllData(info)
            }

        }

        return applicationData.userInfo

    }

}
