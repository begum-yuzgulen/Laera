package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.services.TopicService
import com.yuzgulen.laera.utils.ICallback

class HasChapters {
    companion object {
        private var INSTANCE: HasChapters? = null
        fun getInstance(): HasChapters {
            if (INSTANCE == null) {
                INSTANCE = HasChapters()
            }
            return INSTANCE!!
        }
    }

    fun execute(topicId: String, callback: ICallback<Boolean>) : Any {
        return TopicService.getInstance().hasChapters(topicId, callback)
    }
}