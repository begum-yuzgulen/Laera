package com.yuzgulen.laera.domain.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ExerciseScores (
    var title: String = "",
    var failures: Int = 0,
    var tries: Int = 0,
    var times: HashMap<String, Trial> = hashMapOf()
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title,
            "failures" to failures,
            "tries" to tries,
            "times" to times
        )
    }
}