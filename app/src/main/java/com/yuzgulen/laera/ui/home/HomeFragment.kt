package com.yuzgulen.laera.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.yuzgulen.laera.R
import com.yuzgulen.laera.Topic
import com.yuzgulen.laera.TopicAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.ArrayList

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var topicList = ArrayList<Topic>()
    private var created : Int = 0
    private var progressSorting: String? = ""
    private var progressGraphs: String? = ""
    private var progressLists: String? = ""
    private var progressHeaps: String? = ""
    private var progressGreedy: String? = ""
    private var progressBST: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val currentUser = FirebaseAuth.getInstance().currentUser
        val database = FirebaseDatabase.getInstance().reference
        if(currentUser != null) {
            database.child("user").child(currentUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        progressSorting = dataSnapshot.child("progressSorting").getValue(String::class.java)
                        progressGraphs = dataSnapshot.child("progressGraphs").getValue(String::class.java)
                        progressLists = dataSnapshot.child("progressLists").getValue(String::class.java)
                        progressHeaps = dataSnapshot.child("progressHeaps").getValue(String::class.java)
                        progressGreedy = dataSnapshot.child("progressGreedy").getValue(String::class.java)
                        progressBST = dataSnapshot.child("progressBST").getValue(String::class.java)
                        if (created == 0) {
                            topicList.add(Topic("Graphs", R.drawable.topic_graphs, "Progress: ${progressGraphs}%"))
                            topicList.add(Topic("Sorting Algorithms", R.drawable.topic_sorting, "Progress: ${progressSorting}%"))
                            topicList.add(Topic("Lists", R.drawable.topic_lists, "Progress: ${progressLists}%"))
                            topicList.add(Topic("Binary Search Tree", R.drawable.topic_bst, "Progress: ${progressBST}%"))
                            topicList.add(Topic("Heaps", R.drawable.topic_heaps, "Progress: ${progressHeaps}%"))
                            topicList.add(Topic("Greedy Approach", R.drawable.topic_greedy, "Progress: ${progressGreedy}%"))
                            created = 1
                        }
                        else {
                            topicList[0].progress = "Progress: ${progressGraphs}%"
                            topicList[1].progress = "Progress: ${progressSorting}%"
                            topicList[2].progress = "Progress: ${progressLists}%"
                            topicList[3].progress = "Progress: ${progressBST}%"
                            topicList[4].progress = "Progress: ${progressHeaps}%"
                            topicList[5].progress = "Progress: ${progressGreedy}%"
                        }
                        val adapter = TopicAdapter(topicList, view!!.findNavController())
                        root.gvTopics.adapter = adapter

                        /*var selectedItem: String
                        root.gvTopics.setOnItemClickListener { parent, view, position, _ ->
                            selectedItem = parent.getItemAtPosition(position).toString()
                            Toast.makeText(activity!!.applicationContext, "DO i even get here? $selectedItem", Toast.LENGTH_LONG).show()
                            var prog : String = "0"
                            when(selectedItem){
                                "Sorting Algorithms" -> {prog = progressSorting!!}
                                "Graphs" -> {prog = progressGraphs!!}
                                "Greedy Approach" -> {prog = progressGreedy!!}
                                "Lists" -> {prog = progressLists!!}
                                "Binary Search Tree" -> {prog = progressBST!!}
                                "Heaps" -> {prog = progressHeaps!!}
                            }
                            view.findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToLessonFragment(selectedItem, prog)
                            )
                        }*/
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        }
        else{
            Toast.makeText(context, "User is null", Toast.LENGTH_LONG).show()
        }

        return root
    }
}