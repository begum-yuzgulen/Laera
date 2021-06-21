package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.services.ScoreService
import com.yuzgulen.laera.utils.ICallback

class GetExerciseFinishTimes {
    companion object {
        private var INSTANCE: GetExerciseFinishTimes? = null
        fun getInstance(): GetExerciseFinishTimes {
            if (INSTANCE == null) {
                INSTANCE = GetExerciseFinishTimes()
            }
            return INSTANCE!!
        }
    }

    fun execute(exerciseId: String, callback: ICallback<List<String>>) : Any {
        return ScoreService.getInstance().getExerciseFinishTimes(exerciseId, callback)
    }
}