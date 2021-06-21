package com.yuzgulen.laera.services

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.yuzgulen.laera.domain.models.ExerciseScores
import com.yuzgulen.laera.domain.models.QuizScores
import com.yuzgulen.laera.ui.exercise.categories.sorting.Exercise
import com.yuzgulen.laera.utils.ICallback

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

    val database = FirebaseDatabase.getInstance().reference
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser!!

    fun getQuizScores(uid: String, callback: ICallback<List<QuizScores>>) {
        Firebase.database.reference.child("scores").child("$uid/quizzes").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val scores: MutableList<QuizScores> = mutableListOf()
                for (postSnapshot in dataSnapshot.children) {
                    scores.add(postSnapshot.getValue<QuizScores>()!!)
                }
                callback.onCallback(scores.toList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting list of topics failed
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    fun getExerciseScores(uid: String, callback: ICallback<List<ExerciseScores>>) {
        Firebase.database.reference.child("scores").child("$uid/exercises").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val exerciseScores: MutableList<ExerciseScores> = mutableListOf()
                for (postSnapshot in dataSnapshot.children) {
                    postSnapshot.getValue<ExerciseScores>()?.let { exerciseScores.add(it) }
                }
                callback.onCallback(exerciseScores.toList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting list of topics failed
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    fun getExerciseFinishTimes(exerciseId: String, callback: ICallback<List<String>>) {
        val uid = user.uid
        Firebase.database.reference.child("scores").child("$uid/exercises/$exerciseId")
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val exerciseFinishTimes: MutableList<String> = mutableListOf()
                val exerciseScore = dataSnapshot.getValue<ExerciseScores>()
                if (exerciseScore != null) {
                    for ((key, value) in exerciseScore.times) {
                        exerciseFinishTimes.add(value.time!!)
                    }
                }
                callback.onCallback(exerciseFinishTimes.toList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting list of topics failed
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    fun updateScore(exerciseId: String, exerciseTitle: String, finishTime: String, success: Boolean) {
        database.child("scores").child(user.uid).child("exercises").child(exerciseId).child("title").setValue(exerciseTitle)
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