package com.yuzgulen.laera.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Progress {
    @SerializedName("progress")
    @Expose
    var progress: String? = ""
    @SerializedName("title")
    @Expose
    var title: String? = ""
}