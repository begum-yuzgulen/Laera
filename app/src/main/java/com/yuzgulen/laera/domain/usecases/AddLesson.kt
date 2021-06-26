package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.domain.models.Chapter
import com.yuzgulen.laera.domain.models.Topic
import com.yuzgulen.laera.services.TopicService
import com.yuzgulen.laera.utils.ICallback

class AddLesson {
    companion object {
        private var INSTANCE: AddLesson? = null
        fun getInstance(): AddLesson {
            if (INSTANCE == null) {
                INSTANCE = AddLesson()
            }
            return INSTANCE!!
        }
    }

    fun execute(topic: Topic) : Any {
        return TopicService.getInstance().addTopic(topic)
    }
}