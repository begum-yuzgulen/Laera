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
    private val service = retrofit.create(TopicService::class.java)
    val currentUser = FirebaseAuth.getInstance().currentUser
    private var created : Int = 0

    var topicList = java.util.ArrayList<Topic>()


    fun getAllTopics() {
        val call = service.getTopics()
        call.enqueue(object : Callback<List<TopicResponse>> {
            override fun onResponse(call: Call<List<TopicResponse>>, response: Response<List<TopicResponse>>) {
                if (response.code() == 200) {
                    val topics = response.body()
                    val userService = retrofit.create(UserService::class.java)
                    val userCall = userService.getCurrentUser(currentUser!!.uid)
                    userCall.enqueue(object : Callback<UserResponse> {
                        override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                            if (response.code() == 200) {
                                val user = response.body()
                                if (created == 0) {
                                    for (topic in topics) {
                                        topicList.add(
                                            Topic(
                                                topic.title!!,
                                                Drawables.getIdentifier(topic.icon),
                                                "Progress: ${user.progressGraphs}%"
                                            )
                                        )
                                        created = 1
                                    }
                                }
                                else {
                                    for (topic in topicList) {
                                        topic.progress = "Progress: ${user.progressGraphs}%"
                                    }
                                }

                                _topicList.value = topicList
                            }
                        }


                        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                            //root.text_username!!.text = t.message
                        }
                    })
                }
            }

            override fun onFailure(call: Call<List<TopicResponse>>, t: Throwable) {
                //root.allTopics!!.text = t.message
            }
        })
    }


}