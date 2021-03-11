package com.yuzgulen.laera.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel : ViewModel() {



    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }

    private val _auth = MutableLiveData<FirebaseAuth>().apply {
        value = FirebaseAuth.getInstance()
    }


    val text: LiveData<String> = _text
    val auth: LiveData<FirebaseAuth> = _auth

}