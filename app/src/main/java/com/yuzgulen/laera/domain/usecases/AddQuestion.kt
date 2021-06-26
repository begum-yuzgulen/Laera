package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.domain.models.Question
import com.yuzgulen.laera.domain.models.Topic
import com.yuzgulen.laera.services.TopicService

class AddQuestion {
    companion object {
        private var INSTANCE: AddQuestion? = null
        fun getInstance(): AddQuestion {
            if (INSTANCE == null) {
                INSTANCE = AddQuestion()
            }
            return INSTANCE!!
        }
    }

    fun execute(topicId: String, question: Question) : Any {
        return TopicService.getInstance().addQuestion(topicId, question)
    }
}