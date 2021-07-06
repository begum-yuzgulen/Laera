package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.services.ProgressService

class ResetProgress {
    companion object {
        private var INSTANCE: ResetProgress? = null
        fun getInstance(): ResetProgress {
            if (INSTANCE == null) {
                INSTANCE = ResetProgress()
            }
            return INSTANCE!!
        }
    }

    fun execute(uid: String) : Any {
        return ProgressService.getInstance().resetProgress(uid)
    }
}