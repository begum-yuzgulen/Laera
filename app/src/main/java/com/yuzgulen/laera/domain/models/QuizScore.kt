package com.yuzgulen.laera.domain.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class QuizScore (
    var score: Int? = null,
    var date: String? = null
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "score" to score,
            "date" to date
        )
    }

    override fun toString(): String {
        return "Score: " + score.toString() + " | " +"Date: " + date
    }
}