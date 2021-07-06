package com.yuzgulen.laera.ui.leaderboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.models.QuizScore
import com.yuzgulen.laera.domain.models.QuizScores
import kotlinx.android.synthetic.main.fragment_leaderboard.view.*


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
                val exScores : HashMap<String, QuizScore> = hashMapOf()
                v.forEach{
                    for ((k2, v2) in it) {
                        exScores[k2.username.toString()] = QuizScore(v2, null, k2.username, k2.profilePic)
                    }
                }
                exScores.toSortedMap()
                val exCategory = scores.find { s -> s.title == k }
                if (exCategory == null)
                    scores.add(QuizScores(k, exScores, null))
                else exCategory.scores = exScores
            }
            val adapter = ScoreAdapter(requireContext(), scores)
            root.leaderboard_list.adapter = adapter
        })


        return root
    }
}