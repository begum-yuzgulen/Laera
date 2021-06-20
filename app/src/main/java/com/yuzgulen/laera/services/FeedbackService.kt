package com.yuzgulen.laera.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

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
}