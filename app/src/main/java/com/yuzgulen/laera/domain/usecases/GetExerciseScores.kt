package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.domain.models.ExerciseScores
import com.yuzgulen.laera.domain.models.QuizScores
import com.yuzgulen.laera.services.ScoreService
import com.yuzgulen.laera.utils.ICallback

class GetExerciseScores {
    companion object {
        private var INSTANCE: GetExerciseScores? = null
        fun getInstance(): GetExerciseScores {
            if (INSTANCE == null) {
                INSTANCE = GetExerciseScores()
            }
            return INSTANCE!!
        }
    }

    fun execute(uid: String, callback: ICallback<List<ExerciseScores>>) : Any {
        return ScoreService.getInstance().getExerciseScores(uid, callback)
    }
}