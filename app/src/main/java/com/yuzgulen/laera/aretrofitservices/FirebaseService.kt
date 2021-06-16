package com.yuzgulen.laera.aretrofitservices

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yuzgulen.laera.User


object FirebaseService {
    private const val TAG = "FirebaseService"
    val database = FirebaseDatabase.getInstance().reference
    val auth = FirebaseAuth.getInstance()
    fun getUser(): User? {
        val currentUser = auth.currentUser
        var user: User? = null
        if(currentUser != null) {
            database.child("user").child(currentUser.uid).addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    user = dataSnapshot.getValue(User::class.java)
                }
            })
        }
        return user
    }
}