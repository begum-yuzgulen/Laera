package com.yuzgulen.laera.services

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.yuzgulen.laera.models.Chapter

class ChapterResponse {
    @SerializedName("layout")
    @Expose
    var layout: String? = ""
    @SerializedName("title")
    @Expose
    var title: String? = ""
    @SerializedName("content")
    @Expose
    var content: String? = ""
    @SerializedName("image")
    @Expose
    var image: String? = ""
}