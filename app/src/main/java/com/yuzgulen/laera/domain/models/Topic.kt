package com.yuzgulen.laera.domain.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

//class Topic {
//    @SerializedName("id")
//    @Expose
//    var id: String? = ""
//    @SerializedName("title")
//    @Expose
//    var title: String? = ""
//    @SerializedName("icon")
//    @Expose
//    var icon: String? = ""
//    @SerializedName("nr_chapters")
//    @Expose
//    var nr_chapters: Int? = 0
//}

@IgnoreExtraProperties
data class Topic(
    var id: String? = null,
    var title: String? = null,
    var icon: String? = null,
    var nr_chapters: Int? = 0,
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