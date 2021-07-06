package com.yuzgulen.laera.ui.leaderboard

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.squareup.picasso.Picasso
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.models.QuizScores
import kotlinx.android.synthetic.main.leaderboard_entry.view.*
import kotlinx.android.synthetic.main.lesson_score_entry.view.*

class ScoreAdapter(private val context: Context,
                   private val dataSource: List<QuizScores>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun isEnabled(position: Int): Boolean {
        return false
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val lessonScoreEntry = inflater.inflate(R.layout.lesson_score_entry, parent, false)
        lessonScoreEntry.lesson_title.text = dataSource[position].title
        dataSource[position].scores.forEach {
            val scoreCard = inflater.inflate(
                R.layout.leaderboard_entry, lessonScoreEntry.findViewById(
                    R.id.scores), false)
            scoreCard.userScore.text = it.value.toString()
            Picasso.get().load(it.value.profilePic).into(scoreCard.profilePic)
            lessonScoreEntry.scores.addView(scoreCard)
        }
        return lessonScoreEntry
    }

}