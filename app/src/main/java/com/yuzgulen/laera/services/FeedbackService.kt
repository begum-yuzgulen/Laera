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
import com.google.firebase.storage.FirebaseStorage
import com.yuzgulen.laera.domain.models.Feedback
import com.yuzgulen.laera.utils.ICallback

class FeedbackService {
    companion object {
        private var INSTANCE: FeedbackService? = null
        fun getInstance(): FeedbackService {
            if (INSTANCE == null) {
                INSTANCE = FeedbackService()
            }
            return INSTANCE!!
        }
    }
    private val dbRef = Firebase.database.reference

    fun sendFeedback(message: String, anonymous: Boolean, image: ByteArray?) {

        val feedbackId = dbRef.child("feedback").push().key
        dbRef.child("feedback").child(feedbackId.toString()).child("message").setValue(message)
        if (!anonymous) {
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser!!.uid
            dbRef.child("feedback").child(feedbackId.toString()).child("uid").setValue(user)
        }

        if(image != null) {
            uploadImageToCloudStorage(image, feedbackId.toString())
        }
    }

    private fun uploadImageToCloudStorage(image: ByteArray, feedbackId: String) {
        val imageStorageRef = FirebaseStorage.getInstance().reference.child("feedback/$feedbackId.jpg")
        imageStorageRef.putBytes(image).addOnSuccessListener {
            imageStorageRef.downloadUrl.addOnSuccessListener {
                dbRef.child("feedback").child(feedbackId).child("imageLocation").setValue(it.toString())
            }
        }
    }

    fun getFeedback(callback: ICallback<List<Feedback>>) {
        Firebase.database.reference.child("feedback").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val feedbacks: MutableList<Feedback> = mutableListOf()
                for (postSnapshot in dataSnapshot.children) {
                    val feedback = postSnapshot.getValue<Feedback>()!!
                    feedbacks.add(feedback)
                }
                callback.onCallback(feedbacks.toList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "getFeedback:onCancelled", databaseError.toException())
            }
        })
    }
}