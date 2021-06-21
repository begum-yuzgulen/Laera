package com.yuzgulen.laera.domain.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class QuizScores (
    var title: String? = null,
    var scores: List<QuizScore> = listOf(),
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title,
            "scores" to scores
        )
    }
}