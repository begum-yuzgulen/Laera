package com.example.laera

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yuzgulen.laera.R
import kotlinx.android.synthetic.main.topics_entry.view.*

internal class TopicAdapter : BaseAdapter {
    var foodsList = ArrayList<Topic>()
    var context: Context? = null

    constructor(context: Context, foodsList: ArrayList<Topic>) : super() {
        this.context = context
        this.foodsList = foodsList
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return foodsList.size
    }

    override fun getItem(position: Int): String? {
        return foodsList[position].name
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    fun getProgressAtPosition(position: Int) : String? {
        var prog = foodsList[position].progress
        return prog!!.substring(10, prog.length-1)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val food = this.foodsList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var foodView = inflator.inflate(R.layout.topics_entry, null)
        foodView.imgTopic.setImageResource(food.image!!)
        foodView.topicName.text = food.name!!
        foodView.topicProgress.text = food.progress!!
        return foodView
    }
}