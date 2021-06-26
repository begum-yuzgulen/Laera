package com.yuzgulen.laera.domain.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Feedback (
    var message: String? = null,
    var imageLocation: String? = null,
    var uid : String? = null
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "message" to message,
            "imageLocation" to imageLocation,
            "uid" to uid
        )
    }
}
