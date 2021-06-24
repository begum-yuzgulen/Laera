package com.yuzgulen.laera.domain.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Topic(
    var id: String? = null,
    var title: String? = null,
    var icon: String? = null,
    var nr_chapters: Int? = 1,
    var progress: String? = null
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "icon" to icon,
            "nr_chapters" to nr_chapters,
            "progress" to progress
        )
    }
}