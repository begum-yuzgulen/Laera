package com.yuzgulen.laera.services

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProgressResponse {
    @SerializedName("id")
    @Expose
    var id: String? = ""
    @SerializedName("title")
    @Expose
    var title: String? = ""
    @SerializedName("progress")
    @Expose
    var progress: Int? = 0
}