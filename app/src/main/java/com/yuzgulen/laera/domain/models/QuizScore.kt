package com.yuzgulen.laera.domain.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class QuizScore (
    var score: Float? = null,
    var date: String? = null,
    var uid: String? = null,
    var profilePic: String? = null
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "score" to score,
            "date" to date
        )
    }

    override fun toString(): String {
        if (uid == null)
            return "Score: " + score.toString() + " | " +"Date: " + date
        return uid + " - " + score.toString()
    }
}