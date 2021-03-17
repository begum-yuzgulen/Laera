package com.yuzgulen.laera.ui.exercise

import android.R.attr.button
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_exercise.*
import android.R.attr.startX
import android.opengl.ETC1.getHeight
import android.view.ViewTreeObserver
import android.widget.TextView
import com.google.android.material.internal.ViewUtils.addOnGlobalLayoutListener






class ExerciseFragment : Fragment() {

    private lateinit var exerciseViewModel: ExerciseViewModel

    private fun drawEdgeOnLeft(button1: TextView, button2: TextView, canvas: CanvasView) {
        val startX = button1.left.toFloat()
        val startY = button1.top.toFloat()
        val stopX = button2.left.toFloat()
        val stopY = button2.bottom.toFloat()
        canvas.setCoordinates(startX,startY, stopX,stopY)
    }

    private fun drawEdgeOnRight(button1: TextView, button2: TextView, canvas: CanvasView) {
        val startX = button1.right.toFloat()
        val startY = button1.top.toFloat()
        val stopX = button2.right.toFloat()
        val stopY = textButton3.bottom.toFloat()
        canvas.setCoordinates(startX,startY, stopX,stopY )
    }

    private fun drawRandomBinaryTree() {
        val root = (0..99).random()
        textButton1.text = root.toString()
        // left sub tree
        if ( Math.random() < 0.95) {
            // root has left child => node2
            val node2 = (0..99).random()
            textButton2.text = node2.toString()
            drawEdgeOnLeft(textButton1, textButton2, edge1)
            if (Math.random() < 0.7) {
                // node2 has right child => node4
                val node4 = (0..99).random()
                textButton4.text = node4.toString()
                drawEdgeOnLeft(textButton2, textButton4, edge3)
            } else {
                textButton4.visibility = View.INVISIBLE
                textButton5.visibility = View.INVISIBLE
            }
            if (Math.random() < 0.7) {
                // node2 has right child => node3
                val node5 = (0..99).random()
                textButton5.text = node5.toString()
                drawEdgeOnRight(textButton2, textButton5, edge4)
            } else textButton5.visibility = View.INVISIBLE
        } else {
            textButton2.visibility = View.INVISIBLE
            textButton4.visibility = View.INVISIBLE
            textButton5.visibility = View.INVISIBLE
        }
        // right sub tree
        if ( Math.random() < 0.95 ) {
            // root has right child => node3
            val node3 = (0..99).random()
            textButton3.text = node3.toString()
            drawEdgeOnRight(textButton1, textButton3, edge2)
            if ( Math.random() < 0.85 ) {
                // node3 has left child => node6
                val node6 = (0..99).random()
                textButton6.text = node6.toString()
                drawEdgeOnLeft(textButton3, textButton6, edge5)
            } else {
                textButton6.visibility = View.INVISIBLE
                textButton7.visibility = View.INVISIBLE
            }
            if ( Math.random() < 0.7 ) {
                // node3 has right child => node7
                val node7 = (0..99).random()
                textButton7.text = node7.toString()
                drawEdgeOnRight(textButton3, textButton7, edge6)
            } else textButton7.visibility = View.INVISIBLE
        } else {
            textButton3.visibility = View.INVISIBLE
            textButton6.visibility = View.INVISIBLE
            textButton7.visibility = View.INVISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exerciseViewModel =
            ViewModelProviders.of(this).get(ExerciseViewModel::class.java)
        val root = inflater.inflate(com.yuzgulen.laera.R.layout.fragment_exercise, container, false)

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                drawRandomBinaryTree()

            }
        })

    }
}