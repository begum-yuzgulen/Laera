package com.yuzgulen.laera.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yuzgulen.laera.domain.models.Topic
import com.yuzgulen.laera.domain.usecases.GetTopics
import com.yuzgulen.laera.domain.models.Progress
import com.yuzgulen.laera.domain.usecases.GetProgresses
import com.yuzgulen.laera.utils.ICallback

class HomeViewModel : ViewModel() {


    private val _topicList = MutableLiveData<ArrayList<Topic>>()
    val topicsList: LiveData<ArrayList<Topic>> = _topicList
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private var created : Int = 0

    var topicList = java.util.ArrayList<Topic>()


    fun getAllTopics() {
        var topics: List<Topic>
        GetTopics().execute(object: ICallback<List<Topic>> {
            override fun onCallback(value: List<Topic>) {
                topics = value
                getProgresses(topics)
            }
        })
    }

    fun removeTopicsList() {
        _topicList.value?.clear()
        created = 0
    }

    private fun getProgresses(topics: List<Topic>) {
        GetProgresses().execute(currentUser!!.uid, object: ICallback<List<Progress>> {
            override fun onCallback(value: List<Progress>) {
                if (created == 0) {
                    loadTopics(topics, value)
                    created = 1
                }
                _topicList.value = topicList
            }
        })
    }

    private fun getTopicProgress(progresses: List<Progress>, topicId: String) : Int {
        val topicsProgressData = progresses.find { it.id == topicId }
        val topicsProgress = if (topicsProgressData != null) topicsProgressData.progress else "0"
        return topicsProgress!!.toInt()
    }

    private fun loadTopics(topics: List<Topic>, progresses: List<Progress>) {
        for (topic in topics) {
            val topicsProgress = getTopicProgress(progresses, topic.id!!)
            if (topic.nrChapters != null && topic.nrChapters!! > 0)
            topicList.add(
                Topic(
                    if(topic.id != null) topic.id else "",
                    if(topic.title != null) topic.title else "",
                    if(topic.icon != null) topic.icon else "",
                    0,
                    "Progress: ${(100*topicsProgress) / topic.nrChapters!! }%"
                )
            )
        }
    }

}