package com.yuzgulen.laera.services

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.yuzgulen.laera.domain.models.Chapter
import com.yuzgulen.laera.domain.models.Topic
import com.yuzgulen.laera.utils.ICallback


class TopicService {

    companion object {
        private var INSTANCE: TopicService? = null
        fun getInstance(): TopicService {
            if (INSTANCE == null) {
                INSTANCE = TopicService()
            }
            return INSTANCE!!
        }
    }

    fun getTopics(cb: ICallback<List<Topic>>) {
        // Get a list of Topic objects
        Firebase.database.reference.child("topics").addListenerForSingleValueEvent(object :
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
                Log.w(TAG, "getTopics:onCancelled", databaseError.toException())
            }
        })
    }

    fun getTopicChapters(topicId: String, cb: ICallback<List<Chapter>>) {
        // Get a list of Topic chapters objects
        Firebase.database.reference.child("chapters").child(topicId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val topicChapters: MutableList<Chapter> = mutableListOf()
                for (postSnapshot in dataSnapshot.children) {
                    val chapter = postSnapshot.getValue<Chapter>()!!
                    topicChapters.add(chapter)
                }
                cb.onCallback(topicChapters.toList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "getTopicChapters:onCancelled", databaseError.toException())
            }
        })
    }

    fun getChapterImage(topicId: String, imageName: String) {

    }
}