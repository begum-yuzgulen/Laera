package com.yuzgulen.laera

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.topics_entry.view.*
import android.widget.TextView
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import android.R.xml
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.yuzgulen.laera.ui.home.HomeFragmentDirections


class TopicAdapter(private val dataSet: ArrayList<Topic>, private val navController: NavController) :
    RecyclerView.Adapter<TopicAdapter.ViewHolder>() {


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topicCard: Any
        val topicImage: ImageView
        val topicTitle: TextView
        val topicProgress: TextView
        val progressIndicator: LinearProgressIndicator

        init {
            topicCard = view.topic_card
            topicImage = view.imgTopic
            topicTitle = view.topicName
            topicProgress = view.topicProgress
            progressIndicator = view.progress_indicator

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.topics_entry, viewGroup, false)




//        navController.navigate(
//            HomeFragmentDirections.actionHomeFragmentToLessonFragment(selectedItem, progress)
//        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.topicImage.setImageResource(dataSet[position].image!!)
        viewHolder.topicTitle.text = dataSet[position].name!!
        viewHolder.topicProgress.text = dataSet[position].progress!!

        var progress = viewHolder.topicProgress.text.toString()
        progress = progress.substring(10, progress.length - 1)
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
