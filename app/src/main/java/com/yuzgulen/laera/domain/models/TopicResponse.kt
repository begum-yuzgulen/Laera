package com.yuzgulen.laera.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TopicResponse {
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
