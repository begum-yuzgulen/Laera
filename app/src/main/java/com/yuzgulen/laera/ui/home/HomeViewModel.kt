package com.yuzgulen.laera.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yuzgulen.laera.domain.models.Topic
import com.yuzgulen.laera.domain.usecases.GetTopics
import com.yuzgulen.laera.domain.models.ProgressResponse
import com.yuzgulen.laera.aretrofitservices.*
import com.yuzgulen.laera.utils.ICallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {


    private val _topicList = MutableLiveData<ArrayList<Topic>>()
    val topicsList: LiveData<ArrayList<Topic>> = _topicList
    private val retrofit = Retrofit2Firebase.get()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private var created : Int = 0

    var topicList = java.util.ArrayList<Topic>()


    suspend fun getAllTopics() {
        var topics: List<Topic> = listOf()
        GetTopics().execute(object: ICallback<List<Topic>> {
            override fun onCallback(value: List<Topic>) {
                topics = value
                getProgresses(topics)
            }
        })
        Log.e("Elelelel", "topic${topics.joinToString { ", " }}")
//        val call = service.getTopics()
//        call.enqueue(object : Callback<List<TopicResponse>> {
//            override fun onResponse(call: Call<List<TopicResponse>>, response: Response<List<TopicResponse>>) {
//                if (response.code() == 200) {
//                    val topics = response.body()
//
//                }
//            }
//            override fun onFailure(call: Call<List<TopicResponse>>, t: Throwable) {
//            }
//        })
    }

    private fun getProgresses(topics: List<Topic>) {
        val userService = retrofit.create(UserService::class.java)
        val progressCall = userService.getProgressUser(currentUser!!.uid)
        progressCall.enqueue(object : Callback<ProgressResponse> {
            override fun onResponse(call: Call<ProgressResponse>, response: Response<ProgressResponse>) {
                if (response.code() == 200) {
                    val progresses = response.body()
                    if (created == 0) {
                        loadTopics(topics, progresses)
                        created = 1
                    }
                    _topicList.value = topicList
                }
            }
            override fun onFailure(call: Call<ProgressResponse>, t: Throwable) {
                Log.e("error", t.message!!)
            }
        })
    }

    private fun getTopicProgress(progresses: ProgressResponse, topicId: String) : Int {
        val topicsProgressData = progresses.progresses[topicId]
        val topicsProgress = if (topicsProgressData != null) topicsProgressData.progress else "0"
        return topicsProgress!!.toInt()
    }

    private fun loadTopics(topics: List<Topic>, progresses: ProgressResponse) {
        for (topic in topics) {
            val topicsProgress = getTopicProgress(progresses, topic.id!!)
            topicList.add(
                Topic(
                    if(topic.id != null) topic.id else "",
                    if(topic.title != null) topic.title else "",
                    if(topic.icon != null) topic.icon else "",
                    0,
                    "Progress: ${topicsProgress}%"
                )
            )
        }
    }

}