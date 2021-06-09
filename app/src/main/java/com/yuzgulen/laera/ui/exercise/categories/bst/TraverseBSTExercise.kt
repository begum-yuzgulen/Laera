package com.yuzgulen.laera.ui.exercise.categories.bst

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar

import com.yuzgulen.laera.R
import com.yuzgulen.laera.algorithms.BinaryTree
import com.yuzgulen.laera.algorithms.Node
import com.yuzgulen.laera.ui.exercise.CanvasView
import kotlinx.android.synthetic.main.traverse_bstexercise_fragment.*


class TraverseBSTExercise : Fragment() {

    private lateinit var viewModel: TraverseBSTExerciseViewModel
    private lateinit var correctOrder: List<Int>
    var nodes: MutableList<Int> = mutableListOf()

    private lateinit var traversalType: String

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
        val stopY = button2.bottom.toFloat()
        canvas.setCoordinates(startX,startY, stopX,stopY )
    }



    private fun drawRandomBinaryTree() {
        viewModel.generateRandomTree()
        viewModel.nodesMap.observe(this, Observer {
            textButton1.text = it["root"].toString()
            textButton1.visibility = View.VISIBLE

            // root has left child => node2
            if ("node2" in it) {
                textButton2.text = it["node2"].toString()
                drawEdgeOnLeft(textButton1, textButton2, edge1)
                textButton2.visibility = View.VISIBLE
                // node2 has left child => node4
                if("node4" in it) {
                    textButton4.text = it["node4"].toString()
                    drawEdgeOnLeft(textButton2, textButton4, edge3)
                    textButton4.visibility = View.VISIBLE
                }
                // node2 has right child => node5
                if ("node5" in it) {
                    textButton5.text = it["node5"].toString()
                    drawEdgeOnRight(textButton2, textButton5, edge4)
                    textButton5.visibility = View.VISIBLE
                }
            }

            if ("node3" in it) {
                textButton3.text = it["node3"].toString()
                drawEdgeOnRight(textButton1, textButton3, edge2)
                textButton3.visibility = View.VISIBLE

                if("node6" in it) {
                    textButton6.text = it["node6"].toString()
                    drawEdgeOnLeft(textButton3, textButton6, edge5)
                    textButton6.visibility = View.VISIBLE
                }

                if("node7" in it) {
                    textButton7.text = it["node7"].toString()
                    drawEdgeOnRight(textButton3, textButton7, edge6)
                    textButton7.visibility = View.VISIBLE
                }
            }


        })

        viewModel.bst.observe(this, Observer {
            correctOrder = it.preOrder()
            for (value in correctOrder) {
                inOrder.append("$value ")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.traverse_bstexercise_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "BST Traversal"
        viewModel =
            ViewModelProvider(this).get(TraverseBSTExerciseViewModel::class.java)
        return root
    }

    private fun addButtonToOrder(value: String) {
        val key = value.toInt()
        if (!nodes.contains(key)) {
            val nodeText : Button = LayoutInflater.from(context).inflate(R.layout.node, null) as Button
            nodeText.text = value
            nodeText.setOnClickListener {
                order.removeView(nodeText)
                nodes.remove(value.toInt())
            }
            order.addView(nodeText)
            nodes.add(value.toInt())
        }
        else Snackbar.make(view!!, "Already added $value", Snackbar.LENGTH_SHORT).show()
    }

    fun setNodeOnClickListeners() {
        textButton1.setOnClickListener { addButtonToOrder(textButton1.text.toString()) }
        textButton2.setOnClickListener { addButtonToOrder(textButton2.text.toString()) }
        textButton3.setOnClickListener { addButtonToOrder(textButton3.text.toString()) }
        textButton4.setOnClickListener { addButtonToOrder(textButton4.text.toString()) }
        textButton5.setOnClickListener { addButtonToOrder(textButton5.text.toString()) }
        textButton6.setOnClickListener { addButtonToOrder(textButton6.text.toString()) }
        textButton7.setOnClickListener { addButtonToOrder(textButton7.text.toString()) }
    }

    fun checkIfOrderIsCorrect() : Boolean {
        if ( correctOrder.size != nodes.size) {
            return false
        }
        correctOrder.forEachIndexed { i, value ->
            if (nodes[i] != value)
            {
                return false
            }
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = TraverseBSTExerciseArgs.fromBundle(arguments!!)
        traversalType = args.traversalType
        traversal.text = traversalType
        refreshTree.setOnClickListener {
            view.findNavController().navigate(TraverseBSTExerciseDirections.actionNavTraverseToTraverse(traversalType))
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                drawRandomBinaryTree()
                setNodeOnClickListeners()

                submit.setOnClickListener{
                    val popupView = LayoutInflater.from(context).inflate(R.layout.exercise_popup, null)
                    val correct = checkIfOrderIsCorrect()
                    val resultText = popupView.findViewById<TextView>(R.id.text_view)
                    if(correct) {
                        resultText.text = getString(R.string.correct_traversal_order)
                    } else resultText.text = getString(R.string.wrong_traversal_order)
                    val popupWindow = PopupWindow(
                        popupView, // Custom view to show in popup window
                        LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                        LinearLayout.LayoutParams.WRAP_CONTENT // Window height
                    )
                    val tryAgainButton = popupView.findViewById<Button>(R.id.try_again)
                    val chooseExerciseButton = popupView.findViewById<Button>(R.id.choose_exercise)
                    tryAgainButton.setOnClickListener{
                        popupWindow.dismiss()
                        if(correct){
                            view.findNavController().navigate(TraverseBSTExerciseDirections.actionNavTraverseToTraverse(traversalType))
                        }

                    }

                    chooseExerciseButton.setOnClickListener {
                        view.findNavController().navigate(
                            TraverseBSTExerciseDirections.actionNavTraverseToExercise()
                        )
                    }

                    popupWindow.showAtLocation(exerciseLayout, Gravity.CENTER,0, 0)
                }

            }
        })

    }
}
