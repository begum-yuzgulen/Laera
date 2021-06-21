package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.domain.models.QuizScores
import com.yuzgulen.laera.services.ScoreService
import com.yuzgulen.laera.utils.ICallback

class GetQuizScores {
    companion object {
        private var INSTANCE: GetQuizScores? = null
        fun getInstance(): GetQuizScores {
            if (INSTANCE == null) {
                INSTANCE = GetQuizScores()
            }
            return INSTANCE!!
        }
    }

    fun execute(uid: String, callback: ICallback<List<QuizScores>>) : Any {
        return ScoreService.getInstance().getQuizScores(uid, callback)
    }
}