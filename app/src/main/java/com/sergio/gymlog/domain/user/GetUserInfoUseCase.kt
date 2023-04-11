package com.sergio.gymlog.domain.user

import com.sergio.gymlog.data.model.repository.ApplicationData
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val applicationData: ApplicationData
) {

    operator fun invoke() = applicationData.userInfo

}
