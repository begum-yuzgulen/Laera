package com.yuzgulen.laera

import android.Manifest
import android.content.pm.PackageManager
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.yuzgulen.laera.ui.home.HomeFragmentDirections
import com.yuzgulen.laera.utils.Colors
import kotlinx.android.synthetic.main.topics_entry.view.*
import com.yuzgulen.laera.domain.models.Topic
import com.yuzgulen.laera.domain.usecases.HasQuestions
import com.yuzgulen.laera.ui.home.GeneratePDF
import com.yuzgulen.laera.ui.quiz.QuizFragmentDirections
import com.yuzgulen.laera.utils.App
import com.yuzgulen.laera.utils.ICallback
import kotlin.collections.ArrayList


class TopicAdapter(private val dataSet: ArrayList<Topic>,
                   private val navController: NavController,
                   private val searchView: SearchView) :
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
        val progressButton: Button = view.progressButton
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

        val selectedItem = viewHolder.topicTitle.text.toString()

        viewHolder.itemView.setOnClickListener {
            searchView.clearFocus()
            searchView.removeAllViews()
            HasQuestions.getInstance().execute(filteredDataSet[position].title!!, object : ICallback<Boolean>{
                override fun onCallback(value: Boolean) {
                    if (value) {
                        navController.navigate(
                            HomeFragmentDirections.actionHomeFragmentToLessonFragment(selectedItem, progress, filteredDataSet[position].id!!,
                                filteredDataSet[position].nr_chapters!!)
                        )
                    } else {
                        Toast.makeText(App.instance.applicationContext, "This topic has no questions available!", Toast.LENGTH_LONG).show()
                    }
                }

            })
        }
        viewHolder.progressButton.tag = filteredDataSet[position].id
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
