package com.yuzgulen.laera.ui.leaderboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.yuzgulen.laera.domain.models.ExerciseScores
import com.yuzgulen.laera.domain.models.Trial
import com.yuzgulen.laera.domain.models.User
import com.yuzgulen.laera.domain.usecases.GetLeaderboard
import com.yuzgulen.laera.domain.usecases.GetUser
import com.yuzgulen.laera.services.ScoreService
import com.yuzgulen.laera.utils.ICallback

class LeaderboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    private val _leaderboard = MutableLiveData<MutableMap<String, MutableList<Map<User, Float>>>>()

    val leaderboard: LiveData<MutableMap<String, MutableList<Map<User, Float>>>> = _leaderboard

    val lb : MutableMap<String, MutableList<Map<User, Float>>> = mutableMapOf()

    fun getLeaderboard() {
        GetLeaderboard.getInstance().execute(object : ICallback<Iterable<DataSnapshot>>{
            override fun onCallback(value: Iterable<DataSnapshot>) {
                for (postSnapshot in value) {
                    val uid = postSnapshot.key!!

                    ScoreService.getInstance().getExerciseScores(uid,
                        object : ICallback<List<ExerciseScores>> {
                            override fun onCallback(value: List<ExerciseScores>) {
                                value.forEach {
                                    val meanFinishTime = computeMeanTime(it.times)
                                    val successRate = it.tries - it.failures
                                    val score = (successRate * meanFinishTime) / 100f
                                    getUsername(uid, score, it.title)
                                }
                            }
                        })
                }
            }

        })
    }
    private fun computeMeanTime(times: HashMap<String, Trial>) : Int {
        var sumTimes = 0
        for((key, value) in times) {
            if(value.time != null ) {
                val splitTime: List<String> = value.time!!.split(":")
                val minutes = splitTime[0].toInt()
                val seconds = splitTime[1].toInt()

                sumTimes += minutes*60 + seconds
            }
        }
        return sumTimes
    }

    private fun getUsername(uid: String, score: Float, title: String) {
        GetUser.getInstance().execute(uid, object : ICallback<User> {
            override fun onCallback(value: User) {
                val scorePair = mapOf(value to score)
                if (lb[title] == null) {
                    lb[title] = mutableListOf(scorePair)
                } else lb[title]?.add(scorePair)
                _leaderboard.value = lb
            }
        })
    }
}