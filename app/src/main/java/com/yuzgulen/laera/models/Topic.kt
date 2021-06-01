package com.yuzgulen.laera.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Topic {
    @SerializedName("id")
    @Expose
    var id: String? = ""
    @SerializedName("title")
    @Expose
    var title: String? = ""
    @SerializedName("icon")
    @Expose
    var icon: String? = ""
    @SerializedName("nr_chapters")
    @Expose
    var nr_chapters: Int? = 0
}