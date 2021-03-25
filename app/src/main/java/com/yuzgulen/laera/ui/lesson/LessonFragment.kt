package com.yuzgulen.laera.ui.lesson

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.yuzgulen.laera.R
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.app_bar_main.view.*
import kotlinx.android.synthetic.main.lesson_fragment.*
import kotlinx.android.synthetic.main.lesson_fragment.view.*

class LessonFragment : Fragment() {

    companion object {
        fun newInstance() = LessonFragment()
    }

    private lateinit var viewModel: LessonViewModel
    private lateinit var myRef: DatabaseReference
    private var topic:String = ""
    private var path:String = ""
    private var selectedItem: String = ""
    private var progress: String = ""
    private lateinit var root: View
    private fun lesson(progress: String){
        val identifier = resources.getIdentifier("@android:drawable/presence_online", null, null)
        when ((progress.toInt()/20) + 1) {
            1->{
                root.content.text = getString(context!!.resources.getIdentifier(topic.format(1), "string", context!!.packageName))
                root.next.setOnClickListener {
                    root.prog1.setImageResource(identifier)
                    myRef.setValue("20")
                    lesson("20")
                }
            }
            2-> {
                root.prog1.setImageResource(identifier)
                root.content.text = getString(context!!.resources.getIdentifier(topic.format(2), "string", context!!.packageName))
                root.next.setOnClickListener {
                    root.prog2.setImageResource(identifier)
                    myRef.setValue("40")
                    lesson("40")
                }
            }
            3 -> {
                root.prog1.setImageResource(identifier)
                root.prog2.setImageResource(identifier)
                root.content.text = getString(context!!.resources.getIdentifier(topic.format(3), "string", context!!.packageName))
                root.next.setOnClickListener {
                    root.prog3.setImageResource(identifier)
                    myRef.setValue("60")
                    lesson("60")
                }
            }
            4 -> {
                root.prog1.setImageResource(identifier)
                root.prog2.setImageResource(identifier)
                root.prog3.setImageResource(identifier)
                root.content.text = getString(context!!.resources.getIdentifier(topic.format(4), "string", context!!.packageName))
                root.next.setOnClickListener {
                    root.prog4.setImageResource(identifier)
                    myRef.setValue("80")

                    lesson("80")
                }
            }
            5 -> {
                root.prog1.setImageResource(identifier)
                root.prog2.setImageResource(identifier)
                root.prog3.setImageResource(identifier)
                root.prog4.setImageResource(identifier)
                root.content.text = getString(context!!.resources.getIdentifier(topic.format(5), "string", context!!.packageName))
                root.next.text = "Take quiz"
                root.next.setOnClickListener {
                    root.prog5.setImageResource(identifier)
                    myRef.setValue("100")
//                    view!!.findNavController().navigate(
//                        LessonFragmentDirections.actionLessonFragmentToQuizzFragment(selectedItem)
//                    )
                }
            }
            6-> {
                view!!.findNavController().navigate(
                    LessonFragmentDirections.actionLessonFragmentToQuizzFragment(selectedItem)
                )
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root =  inflater.inflate(R.layout.lesson_fragment, container, false)
        val database = FirebaseDatabase.getInstance().reference
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser!!
        val args = LessonFragmentArgs.fromBundle(arguments!!)
        selectedItem = args.selectedItem
        progress = args.progress
        root.title.text = args.selectedItem
        when(args.selectedItem){
            "Sorting Algorithms" -> {
                topic = "sorting%d"
                path = "progressSorting"
            }
            "Graphs" -> {
                topic = "graphs%d"
                path = "progressGraphs"
            }
            "Lists" -> {
                topic = "lists%d"
                path = "progressLists"
            }
            "Binary Search Tree" -> {
                topic = "bst%d"
                path = "progressBST"
            }
            "Greedy Approach" -> {
                topic = "greedy%d"
                path = "progressGreedy"
            }
            "Heaps" -> {
                topic = "heaps%d"
                path = "progressHeaps"
            }
        }
        myRef = database.child("user").child(user.uid).child(path)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lesson(progress)
    }

}
