package com.sergio.gymlog.domain.training

import com.sergio.gymlog.data.model.training.Training
import javax.inject.Inject

class GetTrainingByIdUseCase @Inject constructor(
   private val getUserTrainingsUseCase: GetUserTrainingsUseCase
) {

    suspend operator fun invoke(id : String) : Training{

        if (id.isNotBlank()) {

            val trainings = getUserTrainingsUseCase()
            for (tr in trainings) {

                if (tr.id == id) {

                    return tr

                }

            }
        }

        return Training()

    }

}