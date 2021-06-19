package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.domain.models.Chapter
import com.yuzgulen.laera.services.TopicService
import com.yuzgulen.laera.utils.ICallback

class GetChapters {

    companion object {
        private var INSTANCE: GetChapters? = null
        fun getInstance(): GetChapters {
            if (INSTANCE == null) {
                INSTANCE = GetChapters()
            }
            return INSTANCE!!
        }
    }

    fun execute(topicId: String, callback: ICallback<List<Chapter>>) : Any {
        return TopicService.getInstance().getTopicChapters(topicId, callback)
    }
}