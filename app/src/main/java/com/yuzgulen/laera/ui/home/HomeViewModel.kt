package com.yuzgulen.laera.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yuzgulen.laera.Topic
import com.yuzgulen.laera.services.*
import com.yuzgulen.laera.utils.Drawables
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


    fun getAllTopics() {
        val service = retrofit.create(TopicService::class.java)
        val call = service.getTopics()
        call.enqueue(object : Callback<List<TopicResponse>> {
            override fun onResponse(call: Call<List<TopicResponse>>, response: Response<List<TopicResponse>>) {
                if (response.code() == 200) {
                    val topics = response.body()
                    getProgresses(topics)
                }
            }
            override fun onFailure(call: Call<List<TopicResponse>>, t: Throwable) {
                //root.allTopics!!.text = t.message
            }
        })
    }

    private fun getProgresses(topics: List<TopicResponse>) {
        val userService = retrofit.create(UserService::class.java)
        val progressCall = userService.getProgressUser(currentUser!!.uid)
        progressCall.enqueue(object : Callback<List<ProgressResponse>> {
            override fun onResponse(call: Call<List<ProgressResponse>>, response: Response<List<ProgressResponse>>) {
                if (response.code() == 200) {
                    val progresses = response.body()
                    if (created == 0) {
                        loadTopics(topics, progresses)
                        created = 1
                    }
                    _topicList.value = topicList
                }
            }
            override fun onFailure(call: Call<List<ProgressResponse>>, t: Throwable) {
                //root.text_username!!.text = t.message
            }
        })
    }

    private fun getTopicProgress(progresses: List<ProgressResponse>, topicId: String) : Int {
        val topicsProgressData = progresses.find { it.id == topicId }
        val topicsProgress = if (topicsProgressData != null) topicsProgressData.progress else 0
        return topicsProgress!!
    }

    private fun loadTopics(topics: List<TopicResponse>, progresses: List<ProgressResponse>) {
        for (topic in topics) {
            val topicsProgress = getTopicProgress(progresses, topic.id!!)
            topicList.add(
                Topic(
                    topic.id!!,
                    topic.title!!,
                    Drawables.getIdentifier(topic.icon),
                    "Progress: ${topicsProgress}%"
                )
            )
        }
    }
}