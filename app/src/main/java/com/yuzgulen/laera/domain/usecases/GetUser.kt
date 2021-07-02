package com.yuzgulen.laera.domain.usecases

import com.yuzgulen.laera.domain.models.User
import com.yuzgulen.laera.services.UserService
import com.yuzgulen.laera.utils.ICallback

class GetUser {
    companion object {
        private var INSTANCE: GetUser? = null
        fun getInstance(): GetUser {
            if (INSTANCE == null) {
                INSTANCE = GetUser()
            }
            return INSTANCE!!
        }
    }

    fun execute(uid: String, callback: ICallback<User>) : Any {
        return UserService.getInstance().getUser(uid, callback)
    }
}