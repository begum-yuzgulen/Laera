package com.yuzgulen.laera.services

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("progressSorting")
    @Expose
    var progressSorting: String? = "0"

    @SerializedName("progressLists")
    @Expose
    var progressLists: String? = "0"

    @SerializedName("progressHeaps")
    @Expose
    var progressHeaps: String? = "0"

    @SerializedName("progressBST")
    @Expose
    var progressBST: String? = "0"

    @SerializedName("progressGreedy")
    @Expose
    var progressGreedy: String? = "0"

    @SerializedName("progressGraphs")
    @Expose
    var progressGraphs: String? = "0"

    @SerializedName("scoreSorting")
    @Expose
    var scoreSorting: String? = "0"

    @SerializedName("scoreLists")
    @Expose
    var scoreLists: String? = "0"

    @SerializedName("scoreHeaps")
    @Expose
    var scoreHeaps: String? = "0"

    @SerializedName("scoreBST")
    @Expose
    var scoreBST: String? = "0"
    @SerializedName("scoreGreedy")
    @Expose
    var scoreGreedy: String? = "0"

    @SerializedName("scoreGraphs")
    @Expose
    var scoreGraphs: String? = "0"

    @SerializedName("uid")
    @Expose
    var uid: String? = ""

    @SerializedName("username")
    @Expose
    var username:String? = ""

    @SerializedName("profilePic")
    @Expose
    var profilePic: String? = ""
}