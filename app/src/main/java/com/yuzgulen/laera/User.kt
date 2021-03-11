package com.yuzgulen.laera

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var progressSorting: String? = "0",
    var progressLists: String? = "0",
    var progressHeaps: String? = "0",
    var progressBST: String? = "0",
    var progressGreedy: String? = "0",
    var progressGraphs: String? = "0",
    var scoreSorting: String? = "0",
    var scoreLists: String? = "0",
    var scoreHeaps: String? = "0",
    var scoreBST: String? = "0",
    var scoreGreedy: String? = "0",
    var scoreGraphs: String? = "0",
    var uid: String? = "",
    var username:String? = "",
    var profilePic: String? = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "progressSorting" to progressSorting,
            "progressLists" to progressLists,
            "progressHeaps" to progressHeaps,
            "progressBST" to progressBST,
            "progressGreedy" to progressGreedy,
            "progressGraphs" to progressGraphs,
            "scoreSorting" to scoreSorting,
            "scoreLists" to scoreLists,
            "scoreHeaps" to scoreHeaps,
            "scoreBST" to scoreBST,
            "scoreGreedy" to scoreGreedy,
            "scoreGraphs" to scoreGraphs,
            "username" to username,
            "profilePic" to profilePic
        )
    }
}