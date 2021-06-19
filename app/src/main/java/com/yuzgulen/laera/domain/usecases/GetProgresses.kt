package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.domain.models.Progress
import com.yuzgulen.laera.services.ProgressService
import com.yuzgulen.laera.utils.ICallback

class GetProgresses {

    companion object {
        private var INSTANCE: GetProgresses? = null
        fun getInstance(): GetProgresses {
            if (INSTANCE == null) {
                INSTANCE = GetProgresses()
            }
            return INSTANCE!!
        }
    }

    fun execute(uid: String, callback: ICallback<List<Progress>>) : Any {
        return ProgressService.getInstance().getUserProgress(uid, callback)
    }
}