package com.yuzgulen.laera.domain.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class QuizScores (
    var title: String? = null,
    var scores: List<QuizScore> = listOf(),
    var profilePic: String? = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title,
            "scores" to scores
        )
    }

    override fun equals(other: Any?): Boolean {
        val otherTitle = (other as QuizScores).title
        if (otherTitle != null)
            return title?.compareTo(otherTitle) == 0
        return false
    }

    override fun hashCode(): Int {
        return title?.hashCode() ?: 0
    }
}