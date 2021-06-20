package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.domain.models.Topic
import com.yuzgulen.laera.services.FeedbackService
import com.yuzgulen.laera.services.TopicService
import com.yuzgulen.laera.utils.ICallback

class SendFeedback {
    companion object {
        private var INSTANCE: SendFeedback? = null
        fun getInstance(): SendFeedback {
            if (INSTANCE == null) {
                INSTANCE = SendFeedback()
            }
            return INSTANCE!!
        }
    }

    fun execute(message: String, anonymous: Boolean, image: ByteArray?) : Any {
        return FeedbackService.getInstance().sendFeedback(message, anonymous, image)
    }
}