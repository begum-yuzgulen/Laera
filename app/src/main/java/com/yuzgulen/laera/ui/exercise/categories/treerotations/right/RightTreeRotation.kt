package com.yuzgulen.laera.ui.exercise.categories.treerotations.right

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yuzgulen.laera.R
import com.yuzgulen.laera.utils.Strings
import com.yuzgulen.laera.ui.exercise.categories.commons.DragShadow
import com.yuzgulen.laera.ui.exercise.categories.commons.CanvasView
import com.yuzgulen.laera.ui.exercise.categories.ExerciseCategory
import com.yuzgulen.laera.ui.exercise.categories.commons.BinaryTreeGeneration
import com.yuzgulen.laera.utils.Colors
import kotlinx.android.synthetic.main.binary_tree.*
import kotlinx.android.synthetic.main.tree_rotation_fragment.*
import kotlinx.android.synthetic.main.tree_rotation_fragment.refreshTree
import kotlinx.android.synthetic.main.tree_rotation_fragment.submit
import kotlinx.android.synthetic.main.tree_rotation_fragment.textButton1
import kotlinx.android.synthetic.main.tree_rotation_fragment.textButton2
import kotlinx.android.synthetic.main.tree_rotation_fragment.textButton3
import kotlinx.android.synthetic.main.tree_rotation_fragment.textButton4
import kotlinx.android.synthetic.main.tree_rotation_fragment.textButton5


class RightTreeRotation : ExerciseCategory() {

    private lateinit var viewModel: RightTreeRotationViewModel
    private lateinit var correctOrder: Array<CharSequence>
    private var btGenerator: BinaryTreeGeneration = BinaryTreeGeneration()

    val rebuildEdges = {
        btGenerator.drawOrDeleteLeftEdge(textButton1, textButton2, edge1, Colors.get(R.color.colorAccent))
        btGenerator.drawOrDeleteRightEdge(textButton1, textButton3, edge2, Colors.get(R.color.colorAccent))
        btGenerator.drawOrDeleteLeftEdge(textButton2, textButton4, edge3, Colors.get(R.color.colorAccent))
        btGenerator.drawOrDeleteRightEdge(textButton2, textButton5, edge4, Colors.get(R.color.colorAccent))
        btGenerator.drawOrDeleteLeftEdge(textButton3, textButton6, edge5, Colors.get(R.color.colorAccent))
        btGenerator.drawOrDeleteRightEdge(textButton3, textButton7, edge6, Colors.get(R.color.colorAccent))
    }

    private fun drawRandomBinaryTree() {
        viewModel.generateRandomTree()
        viewModel.nodesMap.observe(this, {
            val root = it["root"].toString()
            s_node1.text = root
            s_node1.visibility = View.VISIBLE
            needed_node1.text = root
            needed_node1.tag = root
            needed_node1.visibility = View.VISIBLE

            // root has left child => node2
            val node2 = it["node2"].toString()
            s_node2.text = node2
            btGenerator.drawEdgeOnLeft(s_node1, s_node2, s_edge1)
            s_node2.visibility = View.VISIBLE
            needed_node2.text = node2
            needed_node2.tag = node2
            needed_node2.visibility = View.VISIBLE
            // node2 has left child => node4
            val node4 = it["node4"].toString()
            s_node4.text = node4
            btGenerator.drawEdgeOnLeft(s_node2, s_node4, s_edge3)
            s_node4.visibility = View.VISIBLE
            needed_node4.text = node4
            needed_node4.tag = node4
            needed_node4.visibility = View.VISIBLE
            // node2 has right child => node5
            val node5 = it["node5"].toString()
            s_node5.text = node5
            btGenerator.drawEdgeOnRight(s_node2, s_node5, s_edge4)
            s_node5.visibility = View.VISIBLE
            needed_node5.text = node5
            needed_node5.tag = node5
            needed_node5.visibility = View.VISIBLE
            // right tree
            val node3 = it["node3"].toString()
            s_node3.text = node3
            btGenerator.drawEdgeOnRight(s_node1, s_node3, s_edge2)
            s_node3.visibility = View.VISIBLE
            needed_node3.text = node3
            needed_node3.tag = node3
            needed_node3.visibility = View.VISIBLE
        })

        viewModel.bst.observe(this, {
            correctOrder = arrayOf(s_node2.text, s_node4.text, s_node1.text, s_node5.text, s_node3.text)
        })
    }

    private fun drawCorrectEdges() {
        btGenerator.drawEdgeOnLeft(textButton1, textButton2, edge1)
        btGenerator.drawEdgeOnRight(textButton1, textButton3, edge2)
        btGenerator.drawEdgeOnLeft(textButton3, textButton6, edge5)
        btGenerator.drawEdgeOnRight(textButton3, textButton7, edge6)
        btGenerator.drawEdgeOnLeft(textButton2, textButton4, edge3, Colors.get(R.color.transperant))
        btGenerator.drawEdgeOnRight(textButton2, textButton5, edge4, Colors.get(R.color.transperant))

    }

    fun showCorrectResult(buttons: List<Button>, emptyButtons: List<Button>) {
        for (i in 0..correctOrder.size-1) {
            if(buttons[i].text.toString().compareTo(correctOrder[i].toString()) != 0)
                buttons[i].setTextColor( Colors.get(R.color.red))
            buttons[i].text = correctOrder[i]
        }
        emptyButtons.forEach {
            it.text = ""
        }
        drawCorrectEdges()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.tree_rotation_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Binary Tree - Right Rotation"
        viewModel =
            ViewModelProvider(this).get(RightTreeRotationViewModel::class.java)
        return root
    }

    private fun compareResult(currentOrder: Array<CharSequence>): Boolean {
        return currentOrder contentEquals correctOrder
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retryDirection = RightTreeRotationDirections.actionNavRightRotationToRightRotation()
        val exerciseDirection = RightTreeRotationDirections.actionNavRightRotationToExerciseCategory()
        refreshTree.setOnClickListener {
            view.findNavController().navigate(retryDirection)
        }

        info.setOnClickListener {
            showInformation(R.string.loremIpsum)
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                drawRandomBinaryTree()
                startTimer(timer, retryDirection, exerciseDirection)

                btGenerator.setLongClickListeners(listOf(needed_node1, needed_node2,
                    needed_node3, needed_node4, needed_node5))

                btGenerator.setDragListeners(listOf(textButton1, textButton2, textButton3,
                    textButton4, textButton5, textButton6, textButton7), rebuildEdges)

                val allNodeButtons = listOf(textButton1, textButton2, textButton3, textButton4,
                    textButton5, textButton6, textButton7)

                allNodeButtons.forEach { b ->
                    b.setOnClickListener {
                        b.text = null
                        rebuildEdges()
                    }
                }

                submit.setOnClickListener {
                    val currentOrder = arrayOf<CharSequence>(
                        textButton1.text,
                        textButton2.text,
                        textButton3.text,
                        textButton6.text,
                        textButton7.text)
                    val ok = compareResult(currentOrder)

                    val title = if(ok) "Correct" else "Incorrect"
                    val message = if(ok) "Congratulations." else "Your response is not correct, but don't give up! Try again!"
                    viewModel.updateScores(elapsedTime, ok)
                    buildDialog(title, message, retryDirection,exerciseDirection)
                        .setNeutralButton("Show result") { dialog, which ->
                            val buttons = listOf(textButton1, textButton2, textButton3, textButton6, textButton7)
                            val emptyButtons = listOf(textButton4, textButton5)
                            showCorrectResult(buttons, emptyButtons)
                        }
                        .show()
                    
                    cancelTimer()
                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        cancelTimer()
    }
}

