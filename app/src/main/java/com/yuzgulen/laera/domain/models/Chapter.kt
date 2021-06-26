package com.yuzgulen.laera.domain.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Chapter (
    var layout: String? = null,
    var title: String? = null,
    var content: String? = null,
    var image: String? = null
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "layout" to layout,
            "title" to title,
            "content" to content,
            "image" to image
        )
    }
}