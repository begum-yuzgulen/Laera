package com.yuzgulen.laera.ui.quiz

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yuzgulen.laera.domain.models.Question
import com.yuzgulen.laera.domain.models.QuizScores
import com.yuzgulen.laera.domain.usecases.AddQuizScore

class QuizViewModel : ViewModel() {

    val database = FirebaseDatabase.getInstance().reference
    val myRef = database.child("questions")

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question> = _question

    fun getQuestion(topic: String, index: Int) {
        myRef.child(topic).child(index.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val q = dataSnapshot.getValue(Question::class.java)
                    _question.value = q!!
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(ContentValues.TAG, "loadQuestion:onCancelled", error.toException())
                }
            })
    }

    fun addQuizScore(topicId: String, topic: String, scor: Int) {
        AddQuizScore.getInstance().execute(topicId, topic, scor.toFloat())
    }
}
