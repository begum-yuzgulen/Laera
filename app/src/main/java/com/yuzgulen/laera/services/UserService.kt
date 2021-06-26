package com.yuzgulen.laera.services

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.yuzgulen.laera.domain.models.Topic
import com.yuzgulen.laera.utils.ICallback

class UserService {

    companion object {
        private var INSTANCE: UserService? = null
        fun getInstance(): UserService {
            if (INSTANCE == null) {
                INSTANCE = UserService()
            }
            return INSTANCE!!
        }
    }

    fun getUserData(cb: ICallback<List<Topic>>) {
        // Get a list of Topic objects
        Firebase.database.reference.child("progress").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val topics: MutableList<Topic> = mutableListOf()
                for (postSnapshot in dataSnapshot.children) {
                    topics.add(postSnapshot.getValue<Topic>()!!)
                }
                cb.onCallback(topics.toList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting list of topics failed
                Log.w(ContentValues.TAG, "getUserData:onCancelled", databaseError.toException())
            }
        })
    }

    fun getUserName(uid: String, callback: ICallback<String>) {
        Firebase.database.reference.child("user").child(uid).child("username").get().addOnSuccessListener {
            callback.onCallback(it.value.toString())
        }.addOnFailureListener{
            Log.e("Firebase", "Error getting username")
            callback.onCallback("User")
        }
    }

    fun isAdmin(uid: String, callback: ICallback<Boolean>) {
        Firebase.database.reference.child("user").child(uid).child("admin").get().addOnSuccessListener {
            if (it.value != null && it.value == true)
                callback.onCallback(true)
            else callback.onCallback(false)
        }.addOnFailureListener{
            Log.e("Firebase", "Error getting type")
            callback.onCallback(false)
        }
    }

}