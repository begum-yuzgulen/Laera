package com.yuzgulen.laera.domain.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var uid: String? = "",
    var username:String? = "",
    var profilePic: String? = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "username" to username,
            "profilePic" to profilePic
        )
    }

    override fun toString() : String {
        return "User found and retrieved: " + username + uid.toString()
    }
}