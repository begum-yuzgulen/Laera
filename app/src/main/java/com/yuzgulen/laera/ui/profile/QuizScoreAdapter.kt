package com.yuzgulen.laera.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.models.QuizScore
import kotlinx.android.synthetic.main.quiz_score_entry.view.*

class QuizScoreAdapter (private val context: Context,
                        private val dataSource: List<QuizScore>) : BaseAdapter() {

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
        // Get view for row item
        val rowView = inflater.inflate(R.layout.quiz_score_entry, parent, false)
        rowView.score.text = dataSource[position].score.toString() + " | " + dataSource[position].date
        return rowView
    }

}