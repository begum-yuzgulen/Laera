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
        canvas.setCoordinates(startX + 5,startY - 5, stopX - 5,stopY + 5)
    }

    private fun drawEdgeOnRight(button1: TextView, button2: TextView, canvas: CanvasView) {
        val startX = button1.right.toFloat()
        val startY = button1.top.toFloat()
        val stopX = button2.right.toFloat()
        val stopY = textButton3.bottom.toFloat()
        canvas.setCoordinates(startX + 5,startY - 5, stopX - 5,stopY + 5)
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

                drawEdgeOnLeft(textButton1, textButton2, edge1)
                drawEdgeOnRight(textButton1, textButton3, edge2)

            }
        })

    }
}