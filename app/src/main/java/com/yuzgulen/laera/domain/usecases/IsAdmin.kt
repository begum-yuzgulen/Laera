package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.services.UserService
import com.yuzgulen.laera.utils.ICallback

class IsAdmin {
    companion object {
        private var INSTANCE: IsAdmin? = null
        fun getInstance(): IsAdmin {
            if (INSTANCE == null) {
                INSTANCE = IsAdmin()
            }
            return INSTANCE!!
        }
    }

    fun execute(uid: String, callback: ICallback<Boolean>) : Any {
        return UserService.getInstance().isAdmin(uid, callback)
    }
}