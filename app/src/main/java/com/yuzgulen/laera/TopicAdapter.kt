package com.yuzgulen.laera

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.yuzgulen.laera.ui.home.HomeFragmentDirections
import com.yuzgulen.laera.utils.Colors
import kotlinx.android.synthetic.main.topics_entry.view.*
import com.yuzgulen.laera.domain.models.Topic
import kotlin.collections.ArrayList


class TopicAdapter(private val dataSet: ArrayList<Topic>, private val navController: NavController) :
    RecyclerView.Adapter<TopicAdapter.ViewHolder>(), Filterable {

    var filteredDataSet = ArrayList<Topic>()

    init {
        filteredDataSet = dataSet
    }
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topicImage: ImageView = view.imgTopic
        val topicTitle: TextView = view.topicName
        val topicProgress: TextView = view.topicProgress
        val progressIndicator: LinearProgressIndicator = view.progress_indicator
        val quizButton: Button = view.quizButton
    }
    private var itemsCopy = ArrayList(dataSet)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.topics_entry, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val storage = Firebase.storage
        val storageRef = storage.reference

        storageRef.child("lesson_icons/" + filteredDataSet[position].icon!!).downloadUrl.addOnSuccessListener {
            Picasso.get().load(it).into(viewHolder.topicImage)
        }.addOnFailureListener {
            Log.e("storage error", it.message.toString())
        }

        viewHolder.topicTitle.text = filteredDataSet[position].title!!
        viewHolder.topicProgress.text = filteredDataSet[position].progress!!


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
                HomeFragmentDirections.actionHomeFragmentToLessonFragment(selectedItem, progress, filteredDataSet[position].id!!)
            )
        }
    }

    override fun getItemCount() = filteredDataSet.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredDataSet = dataSet
                } else {
                    val resultList = ArrayList<Topic>()
                    for (row in dataSet) {
                        if (row.title!!.lowercase().contains(charSearch.lowercase())) {
                            resultList.add(row)
                        }
                    }
                    filteredDataSet = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredDataSet
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredDataSet = results?.values as ArrayList<Topic>
                notifyDataSetChanged()
            }

        }
    }
}
