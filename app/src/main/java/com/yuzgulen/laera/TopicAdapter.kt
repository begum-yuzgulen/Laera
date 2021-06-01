package com.yuzgulen.laera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.topics_entry.view.*
import android.widget.*
import androidx.navigation.NavController
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.yuzgulen.laera.ui.home.HomeFragmentDirections
import com.yuzgulen.laera.utils.Colors


class TopicAdapter(private val dataSet: ArrayList<Topic>, private val navController: NavController) :
    RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topicImage: ImageView = view.imgTopic
        val topicTitle: TextView = view.topicName
        val topicProgress: TextView = view.topicProgress
        val progressIndicator: LinearProgressIndicator = view.progress_indicator
        val quizButton: Button = view.quizButton
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.topics_entry, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.topicImage.setImageResource(dataSet[position].image!!)
        viewHolder.topicTitle.text = dataSet[position].name!!
        viewHolder.topicProgress.text = dataSet[position].progress!!


        var progress = viewHolder.topicProgress.text.toString()
        progress = progress.substring(10, progress.length - 1)

        if(progress.toInt() == 100 ) {
            viewHolder.quizButton.setEnabled(true)
            viewHolder.quizButton.setTextColor(Colors.get(R.color.colorPrimary))
        }


        viewHolder.progressIndicator.setProgress(progress.toInt(), true)

        var selectedItem = viewHolder.topicTitle.text.toString()

        viewHolder.itemView.setOnClickListener {
            navController.navigate(
                HomeFragmentDirections.actionHomeFragmentToLessonFragment(selectedItem, progress)
            )
        }
    }

    override fun getItemCount() = dataSet.size

}
