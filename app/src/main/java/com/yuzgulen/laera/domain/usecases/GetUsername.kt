package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.domain.models.QuizScores
import com.yuzgulen.laera.services.ScoreService
import com.yuzgulen.laera.services.UserService
import com.yuzgulen.laera.utils.ICallback

class GetUsername {
    companion object {
        private var INSTANCE: GetUsername? = null
        fun getInstance(): GetUsername {
            if (INSTANCE == null) {
                INSTANCE = GetUsername()
            }
            return INSTANCE!!
        }
    }

    fun execute(uid: String, callback: ICallback<String>) : Any {
        return UserService.getInstance().getUserName(uid, callback)
    }
}