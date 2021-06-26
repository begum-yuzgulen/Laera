package com.yuzgulen.laera.services

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.yuzgulen.laera.domain.models.Progress
import com.yuzgulen.laera.utils.ICallback

class ProgressService {

    companion object {
        private var INSTANCE: ProgressService? = null
        fun getInstance(): ProgressService {
            if (INSTANCE == null) {
                INSTANCE = ProgressService()
            }
            return INSTANCE!!
        }
    }

    fun getUserProgress(uid: String, callback: ICallback<List<Progress>>) {
        Firebase.database.reference.child("progress").child(uid).child("progresses").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val progresses: MutableList<Progress> = mutableListOf()
                for (postSnapshot in dataSnapshot.children) {
                    progresses.add(postSnapshot.getValue<Progress>()!!)
                }
                callback.onCallback(progresses.toList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "getUserProgress:onCancelled", databaseError.toException())
            }
        })
    }

    fun updateProgress(uid: String, topicId: String, progress: Int) {
        Firebase.database.reference.child("progress").child(uid).child("progresses")
            .child(topicId).child("progress").setValue(progress.toString())
    }

}