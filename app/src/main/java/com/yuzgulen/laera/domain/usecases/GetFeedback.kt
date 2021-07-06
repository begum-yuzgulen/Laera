package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.domain.models.Feedback
import com.yuzgulen.laera.services.FeedbackService
import com.yuzgulen.laera.utils.ICallback

class GetFeedback {
    companion object {
        private var INSTANCE: GetFeedback? = null
        fun getInstance(): GetFeedback {
            if (INSTANCE == null) {
                INSTANCE = GetFeedback()
            }
            return INSTANCE!!
        }
    }

    fun execute(callback: ICallback<List<Feedback>>) : Any {
        return FeedbackService.getInstance().getFeedback(callback)
    }
}