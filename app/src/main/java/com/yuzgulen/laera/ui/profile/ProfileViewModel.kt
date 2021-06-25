package com.yuzgulen.laera.ui.profile

import android.content.ContentValues.TAG
import android.util.Log
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
import com.yuzgulen.laera.domain.models.ExerciseScores
import com.yuzgulen.laera.domain.models.QuizScores
import com.yuzgulen.laera.domain.usecases.GetExerciseScores
import com.yuzgulen.laera.domain.usecases.GetQuizScores
import com.yuzgulen.laera.utils.ICallback


class ProfileViewModel : ViewModel() {

    private var _userProfile = MutableLiveData<User>()
    val userProfile: LiveData<User> = _userProfile
    private val database = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()
    private var _userQuizScores = MutableLiveData<List<QuizScores>>()
    val userQuizScores: LiveData<List<QuizScores>> = _userQuizScores

    private var _chartUrl = MutableLiveData<String>()

    fun getUser(): LiveData<User> {

        val currentUser = auth.currentUser
        if(currentUser != null) {
            database.child("user").child(currentUser.uid).addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "getUser:onCancelled", error.toException())
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    _userProfile.value = dataSnapshot.getValue(User::class.java)
                }
            })
            GetQuizScores().execute(currentUser.uid, object : ICallback<List<QuizScores>> {
                override fun onCallback(value: List<QuizScores>) {
                    _userQuizScores.value = value
                }
            })
        }

        return _userProfile
    }

    fun getExerciseScores() : LiveData<String> {
        var url = "https://quickchart.io/chart?c={type:'bar',data:{labels:"
        GetExerciseScores().execute(auth.currentUser!!.uid, object : ICallback<List<ExerciseScores>> {
            override fun onCallback(value: List<ExerciseScores>) {
                val labels : MutableList<String> = mutableListOf()
                val successes : MutableList<Int> = mutableListOf()
                val failures : MutableList<Int> = mutableListOf()
                value.forEach {
                    labels.add("'" + it.title + "'")
                    successes.add(it.tries - it.failures)
                    failures.add(it.failures)

                }
                url += labels.toString() +
                        ", datasets:[{label:'Successes', backgroundColor:'green', data:" +
                        successes.toString() + "}, {label:'Failures', backgroundColor:'red', data:"  +
                        failures.toString() + "}]}}"

                _chartUrl.value = url
            }
        })

        return _chartUrl

    }

    fun signOut(googleSignInClient: GoogleSignInClient){
        auth.signOut()
        googleSignInClient.signOut()
    }

}