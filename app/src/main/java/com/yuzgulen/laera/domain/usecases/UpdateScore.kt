package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.services.ScoreService

class UpdateScore {
    companion object {
        private var INSTANCE: GetChapters? = null
        fun getInstance(): GetChapters {
            if (INSTANCE == null) {
                INSTANCE = GetChapters()
            }
            return INSTANCE!!
        }
    }

    fun execute(exerciseId: String, exerciseTitle: String, finishTime: String, success: Boolean) {
        return ScoreService.getInstance().updateScore(exerciseId, exerciseTitle, finishTime, success)
    }
}