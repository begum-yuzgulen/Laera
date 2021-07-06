package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.services.ScoreService
import java.text.SimpleDateFormat
import java.util.*

class AddQuizScore {
    companion object {
        private var INSTANCE: AddQuizScore? = null
        fun getInstance(): AddQuizScore {
            if (INSTANCE == null) {
                INSTANCE = AddQuizScore()
            }
            return INSTANCE!!
        }
    }

    fun execute(topicId: String, title: String, score: Float) {
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(Calendar.getInstance().time)
        return ScoreService.getInstance().addQuizScore(topicId, title, score, formatedDate)
    }
}