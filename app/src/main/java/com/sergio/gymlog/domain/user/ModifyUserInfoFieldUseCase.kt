package com.sergio.gymlog.domain.user


import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.data.repository.user.UserDataRepository
import javax.inject.Inject

class ModifyUserInfoFieldUseCase @Inject constructor(
    private val applicationData: ApplicationData,
    private val userDataRepository: UserDataRepository
){

    suspend operator fun invoke(fieldName : String, fieldData : String){

        when(fieldName){
            UserInfo.USERNAME_TAG -> {
                userDataRepository.modifyUserInfo(applicationData.userInfo.id, fieldName, fieldData)
               applicationData.userInfo.username =  fieldData
            }
            UserInfo.PHOTO_TAG -> {
                userDataRepository.modifyUserInfo(applicationData.userInfo.id, fieldName, fieldData)
                applicationData.userInfo.photo = fieldData
            }
            UserInfo.SETS_TAG -> {
                userDataRepository.modifyUserInfo(applicationData.userInfo.id, fieldName, fieldData.toInt())
                applicationData.userInfo.sets = fieldData.toInt()
            }
            UserInfo.REPETITIONS_TAG -> {
                userDataRepository.modifyUserInfo(applicationData.userInfo.id, fieldName, fieldData.toInt())
                applicationData.userInfo.repetitions = fieldData.toInt()
            }
            UserInfo.WEIGHT_TAG -> {
                userDataRepository.modifyUserInfo(applicationData.userInfo.id, fieldName, fieldData.toDouble())
                applicationData.userInfo.weight = fieldData.toDouble()
            }
            else -> throw Exception("Unaccounted field name")

        }


    }


}