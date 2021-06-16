package com.yuzgulen.laera.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("uid")
    @Expose
    var uid: String? = ""

    @SerializedName("username")
    @Expose
    var username:String? = ""

    @SerializedName("profilePic")
    @Expose
    var profilePic: String? = ""
}