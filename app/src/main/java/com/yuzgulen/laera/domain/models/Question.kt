package com.yuzgulen.laera.domain.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Question(
    var question: String? = "",
    var ans1: String? = "",
    var ans2: String? = "",
    var ans3: String? = "",
    var ans4: String? = "",
    var correct: String? = "",
    var difficulty: String? = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "question" to question,
            "ans1" to ans1,
            "ans2" to ans2,
            "ans3" to ans3,
            "ans4" to ans4,
            "correct" to correct,
            "difficulty" to difficulty
        )
    }
}