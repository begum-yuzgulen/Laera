package com.yuzgulen.laera.domain.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@IgnoreExtraProperties
data class Chapter (
    var layout: String? = null,
    var title: String? = null,
    var content: String? = null
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "layout" to layout,
            "title" to title,
            "content" to content
        )
    }
}