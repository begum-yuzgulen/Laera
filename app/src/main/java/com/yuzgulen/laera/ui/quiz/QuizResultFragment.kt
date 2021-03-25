package com.yuzgulen.laera.ui.quiz


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.yuzgulen.laera.R
import kotlinx.android.synthetic.main.quiz_result_fragment.*


class QuizResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quiz_result_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = QuizResultFragmentArgs.fromBundle(arguments!!)
        val score = args.score
        val topic = args.topic
        var path = ""
        var topicPath =""
        when(topic){
            "Sorting Algorithms" -> {
                path = "topic_sorting"
                topicPath = "Sorting"
            }
            "Lists" -> {
                path = "topic_lists"
                topicPath = "Lists"
            }
            "Greedy Approach" -> {
                path = "topic_greedy"
                topicPath = "Greedy"
            }
            "Graphs" -> {
                path = "topic_graphs"
                topicPath = "Graphs"
            }
            "Binary Search Tree" -> {
                path = "topic_bst"
                topicPath = "BST"
            }
            "Heaps" -> {
                path = "topic_heaps"
                topicPath = "Heaps"
            }
        }
        val resourceId = this.resources.getIdentifier(
            path,
            "drawable",
            activity?.packageName
        )

        win_imageview.setImageResource(resourceId)
        score_textview.text = getString(R.string.congrats).format(score)
        val database = FirebaseDatabase.getInstance().reference
        val user = FirebaseAuth.getInstance().currentUser
        val myRef = database.child("user").child(user!!.uid).child("score" + topicPath)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val pScor = p0.value?.toString()?.toInt() ?: 0
                if (pScor < score) {
                    myRef.setValue(score.toString())
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

        try_again_button.setOnClickListener {
            view.findNavController()
                .navigate(QuizResultFragmentDirections.actionWinFragmentToQuizzFragment(topic))
        }
        new_game_button.setOnClickListener {
            view.findNavController().navigate(R.id.action_quizResultFragment_to_homeFragment)
        }

    }
}
