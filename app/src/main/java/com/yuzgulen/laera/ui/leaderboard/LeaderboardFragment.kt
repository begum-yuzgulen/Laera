package com.yuzgulen.laera.ui.leaderboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.models.QuizScore
import com.yuzgulen.laera.domain.models.QuizScores
import com.yuzgulen.laera.ui.profile.LessonScoreAdapter
import kotlinx.android.synthetic.main.fragment_leaderboard.*
import kotlinx.android.synthetic.main.fragment_leaderboard.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.lesson_score_entry.view.*


class LeaderboardFragment : Fragment() {

    private lateinit var leaderboardViewModel: LeaderboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        leaderboardViewModel =
            ViewModelProviders.of(this).get(LeaderboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_leaderboard, container, false)

        leaderboardViewModel.getLeaderboard()

        val scores : MutableList<QuizScores> = mutableListOf()
        leaderboardViewModel.leaderboard.observe(viewLifecycleOwner, {
            for ((k, v) in it) {
                val exScores : MutableList<QuizScore> = mutableListOf()
                v.forEach{
                    for ((k2, v2) in it) {
                        exScores.add(QuizScore(v2, null, k2.username, k2.profilePic))
                    }
                }
                exScores.sortByDescending {es -> es.score}
                val exCategory = scores.find { s -> s.title == k }
                if (exCategory == null)
                    scores.add(QuizScores(k, exScores))
                else exCategory.scores = exScores
            }
            val adapter = LessonScoreAdapter(requireContext(), scores)
            root.leaderboard_list.adapter = adapter
        })


        return root
    }
}