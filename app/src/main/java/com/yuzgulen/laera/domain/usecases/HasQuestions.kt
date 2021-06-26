package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.services.TopicService
import com.yuzgulen.laera.services.UserService
import com.yuzgulen.laera.utils.ICallback

class HasQuestions {
    companion object {
        private var INSTANCE: HasQuestions? = null
        fun getInstance(): HasQuestions {
            if (INSTANCE == null) {
                INSTANCE = HasQuestions()
            }
            return INSTANCE!!
        }
    }

    fun execute(topicId: String, callback: ICallback<Boolean>) : Any {
        return TopicService.getInstance().hasQuestions(topicId, callback)
    }
}