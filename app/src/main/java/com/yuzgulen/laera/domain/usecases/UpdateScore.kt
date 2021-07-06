package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.services.ScoreService

class UpdateScore {
    companion object {
        private var INSTANCE: UpdateScore? = null
        fun getInstance(): UpdateScore {
            if (INSTANCE == null) {
                INSTANCE = UpdateScore()
            }
            return INSTANCE!!
        }
    }

    fun execute(exerciseId: String, exerciseTitle: String, finishTime: String, success: Boolean) {
        return ScoreService.getInstance().updateScore(exerciseId, exerciseTitle, finishTime, success)
    }
}