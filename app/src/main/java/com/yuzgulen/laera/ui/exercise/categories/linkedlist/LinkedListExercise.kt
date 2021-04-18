package com.yuzgulen.laera.ui.exercise.categories.linkedlist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

import com.yuzgulen.laera.R
import kotlinx.android.synthetic.main.linked_list_exercise_fragment.*


class LinkedListExercise : Fragment() {

    private lateinit var viewModel: LinkedListExerciseViewModel
    private lateinit var correctOrder: List<Int>
    var nodes: MutableList<Int> = mutableListOf()
    var addedKeys: MutableSet<Int> = mutableSetOf()
    private lateinit var traversalType: String

    private fun generateRandomKey(textButton: TextView) : Int {
        val key = (0..99).random()
        if(!addedKeys.contains(key)) {
            addedKeys.add(key)
            textButton.text = (0..99).random().toString()
            textButton.visibility = View.VISIBLE
            return key
        }
        return generateRandomKey(textButton)
    }

    private fun drawRandomLinkedList() {
        generateRandomKey(textButton1)
        generateRandomKey(textButton2)
        generateRandomKey(textButton3)
        generateRandomKey(textButton4)
        generateRandomKey(textButton5)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.linked_list_exercise_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Linked List Insertion"

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                drawRandomLinkedList()

            }
        })

    }
}
