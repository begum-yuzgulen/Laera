package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.services.ProgressService

class UpdateProgress {
    companion object {
        private var INSTANCE: UpdateProgress? = null
        fun getInstance(): UpdateProgress {
            if (INSTANCE == null) {
                INSTANCE = UpdateProgress()
            }
            return INSTANCE!!
        }
    }

    fun execute(uid: String, topicId: String, topicName: String, progress: Int) : Any {
        return ProgressService.getInstance().updateProgress(uid, topicId, topicName, progress)
    }
}