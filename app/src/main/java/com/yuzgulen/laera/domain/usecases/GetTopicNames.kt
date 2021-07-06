package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.services.TopicService
import com.yuzgulen.laera.utils.ICallback

class GetTopicNames {
    companion object {
        private var INSTANCE: GetTopicNames? = null
        fun getInstance(): GetTopicNames {
            if (INSTANCE == null) {
                INSTANCE = GetTopicNames()
            }
            return INSTANCE!!
        }
    }

    fun execute(callback: ICallback<List<String>>) : Any {
        return TopicService.getInstance().getTopicNames(callback)
    }
}