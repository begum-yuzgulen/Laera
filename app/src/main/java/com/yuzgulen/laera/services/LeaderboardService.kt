package com.yuzgulen.laera.services

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yuzgulen.laera.domain.models.Trial
import com.yuzgulen.laera.utils.ICallback

class LeaderboardService {
    companion object {
        private var INSTANCE: LeaderboardService? = null
        fun getInstance(): LeaderboardService {
            if (INSTANCE == null) {
                INSTANCE = LeaderboardService()
            }
            return INSTANCE!!
        }
    }

    private val dbRef = Firebase.database.reference

    val leaderboard: MutableMap<String, Map<String, Float>> = mutableMapOf()

    fun getLeaderboard(callback: ICallback<Iterable<DataSnapshot>>) {

        dbRef.child("scores").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                callback.onCallback(dataSnapshot.children)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "getUserProgress:onCancelled", databaseError.toException())
            }
        })
    }

    private fun getLeaderboardScores(children: Iterable<DataSnapshot>) {

    }

    private fun computeMeanTime(times: HashMap<String, Trial>) : Int {
        var sumTimes = 0
        for((key, value) in times) {
            if(value.time != null ) {
                val splitTime: List<String> = value.time!!.split(":")
                val minutes = splitTime[0].toInt()
                val seconds = splitTime[1].toInt()

                sumTimes += minutes*60 + seconds
            }

        }
        return sumTimes

    }
}