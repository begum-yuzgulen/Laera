package com.yuzgulen.laera

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.models.Feedback
import com.yuzgulen.laera.domain.models.QuizScores
import kotlinx.android.synthetic.main.feedback_entry.view.*
import kotlinx.android.synthetic.main.lesson_score_entry.view.*


class FeedbackAdapter(private val context: Context,
                         private val dataSource: List<Feedback>) : BaseAdapter() {

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

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val feedbackEntry = inflater.inflate(R.layout.feedback_entry, parent, false)
        feedbackEntry.feedbackMessage.text = dataSource[position].message
        Picasso.get().load(dataSource[position].imageLocation).into(feedbackEntry.feedbackImage)
        return feedbackEntry
    }

}
