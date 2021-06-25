package com.yuzgulen.laera.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.models.QuizScores
import kotlinx.android.synthetic.main.lesson_score_entry.view.*


class LessonScoreAdapter(private val context: Context,
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

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.lesson_score_entry, parent, false)
        rowView.lesson_title.text = dataSource[position].title
        Log.d("beee", dataSource[position].scores.toString())
        val adapter = ArrayAdapter(context, android.R.layout.simple_expandable_list_item_1, dataSource[position].scores.toTypedArray())
        //rowView.quizScoresList.adapter = adapter
        var scores = ""
        dataSource[position].scores.forEach {
            val tv = TextView(context)
            tv.setPadding(20,20,20,20)
            tv.text = it.toString()
            rowView.scores.addView(tv)
        }
        //rowView.scores.text = scores
        return rowView
    }

}
