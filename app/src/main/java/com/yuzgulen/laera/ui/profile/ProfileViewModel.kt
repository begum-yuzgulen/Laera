package com.yuzgulen.laera.ui.profile

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yuzgulen.laera.User
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yuzgulen.laera.services.FirebaseService


class ProfileViewModel : ViewModel() {

    private var _userProfile = MutableLiveData<User>()
    val userProfile: LiveData<User> = _userProfile
    private val database = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()


    fun getUser(): LiveData<User> {

        val currentUser = auth.currentUser
        if(currentUser != null) {
            database.child("user").child(currentUser.uid).addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    _userProfile.value = dataSnapshot.getValue(User::class.java)
                }
            })
        }
        return _userProfile
    }

    fun signOut(googleSignInClient: GoogleSignInClient){
        auth.signOut()
        googleSignInClient.signOut()
    }

}