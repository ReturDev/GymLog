package com.sergio.gymlog.domain.training.daily


import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.domain.user.GetUserInfoUseCase
import javax.inject.Inject

class GetDailyTrainingUseCase @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) {

    suspend operator fun invoke () : UserInfo.DailyTraining?{

       return getUserInfoUseCase().dailyTraining

    }

}