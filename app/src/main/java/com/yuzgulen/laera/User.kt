package com.yuzgulen.laera

import android.util.Log
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@IgnoreExtraProperties
data class User(
    @SerializedName("progressSorting")
    @Expose
    var progressSorting: String? = "0",
    @SerializedName("progressLists")
    @Expose
    var progressLists: String? = "0",
    @SerializedName("progressHeaps")
    @Expose
    var progressHeaps: String? = "0",
    @SerializedName("progressBST")
    @Expose
    var progressBST: String? = "0",
    @SerializedName("progressGreedy")
    @Expose
    var progressGreedy: String? = "0",
    @SerializedName("progressGraphs")
    @Expose
    var progressGraphs: String? = "0",
    @SerializedName("scoreSorting")
    @Expose
    var scoreSorting: String? = "0",
    @SerializedName("scoreLists")
    @Expose
    var scoreLists: String? = "0",
    @SerializedName("scoreHeaps")
    @Expose
    var scoreHeaps: String? = "0",
    @SerializedName("scoreBST")
    @Expose
    var scoreBST: String? = "0",
    @SerializedName("scoreGreedy")
    @Expose
    var scoreGreedy: String? = "0",
    @SerializedName("scoreGraphs")
    @Expose
    var scoreGraphs: String? = "0",
    @SerializedName("uid")
    @Expose
    var uid: String? = "",
    @SerializedName("username")
    @Expose
    var username:String? = "",
    @SerializedName("profilePic")
    @Expose
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

    override fun toString() : String {
        return "User found and retrieved: " + username + uid.toString()
    }
}