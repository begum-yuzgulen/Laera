package com.yuzgulen.laera.services

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
}