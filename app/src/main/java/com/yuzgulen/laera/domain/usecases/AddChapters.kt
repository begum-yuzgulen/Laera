package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.domain.models.Chapter
import com.yuzgulen.laera.services.TopicService
import com.yuzgulen.laera.utils.ICallback

class AddChapters {
    companion object {
        private var INSTANCE: AddChapters? = null
        fun getInstance(): AddChapters {
            if (INSTANCE == null) {
                INSTANCE = AddChapters()
            }
            return INSTANCE!!
        }
    }

    fun execute(chapters: List<Chapter>, topicId: String) : Any {
        return TopicService.getInstance().addTopicChapters(chapters, topicId)
    }
}