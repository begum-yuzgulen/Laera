package com.yuzgulen.laera.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ScoreService {

    companion object {
        private var INSTANCE: ScoreService? = null
        fun getInstance(): ScoreService {
            if (INSTANCE == null) {
                INSTANCE = ScoreService()
            }
            return INSTANCE!!
        }
    }

    fun updateScore(exerciseId: String, finishTime: String, success: Boolean) {
        val database = FirebaseDatabase.getInstance().reference
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser!!
        val myRefTries = database.child("scores").child(user.uid).child("exercises").child(exerciseId).child("tries")
        val myRefTimes = database.child("scores").child(user.uid).child("exercises").child(exerciseId).child("times")
        val myRefFailures = database.child("scores").child(user.uid).child("exercises").child(exerciseId).child("failures")
        myRefTries.setValue(ServerValue.increment(1))
        val newTime = myRefTimes.push()
        newTime.child("time").setValue(finishTime)
        newTime.child("success").setValue(success)
        if(!success) myRefFailures.setValue(ServerValue.increment(1))
    }

}