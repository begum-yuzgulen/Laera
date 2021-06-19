package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.services.TopicService
import com.yuzgulen.laera.domain.models.Topic
import com.yuzgulen.laera.utils.ICallback


class GetTopics : UseCase<ICallback<List<Topic>>> {

    companion object {
        private var INSTANCE: GetTopics? = null
        fun getInstance(): GetTopics {
            if (INSTANCE == null) {
                INSTANCE = GetTopics()
            }
            return INSTANCE!!
        }
    }

    override fun execute(callback: ICallback<List<Topic>>) : Any {
        return TopicService.getInstance().getTopics(callback)
    }
}