package com.yuzgulen.laera.ui.exercise.categories.treerotations.right

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.yuzgulen.laera.R
import com.yuzgulen.laera.ui.exercise.categories.ExerciseCategory
import com.yuzgulen.laera.ui.exercise.categories.commons.BinaryTreeGeneration
import com.yuzgulen.laera.utils.Colors
import kotlinx.android.synthetic.main.binary_tree.*
import kotlinx.android.synthetic.main.tree_rotation_fragment.*


class RightTreeRotation : ExerciseCategory() {

    private lateinit var viewModel: RightTreeRotationViewModel
    private lateinit var correctOrder: Array<CharSequence>
    private var btGenerator: BinaryTreeGeneration = BinaryTreeGeneration()
    private lateinit var direction: Direction

    enum class Direction { LEFT, RIGHT }

    val rebuildEdges = {
        btGenerator.drawOrDeleteLeftEdge(textButton1, textButton2, edge1)
        btGenerator.drawOrDeleteRightEdge(textButton1, textButton3, edge2)
        btGenerator.drawOrDeleteLeftEdge(textButton2, textButton4, edge3)
        btGenerator.drawOrDeleteRightEdge(textButton2, textButton5, edge4)
        btGenerator.drawOrDeleteLeftEdge(textButton3, textButton6, edge5)
        btGenerator.drawOrDeleteRightEdge(textButton3, textButton7, edge6)
    }


    private fun drawRandomBinaryTreeForRightRotation() {
        viewModel.generateRandomTree()
        viewModel.nodesMap.observe(this, {
            drawNode(s_node1, it["root"].toString(), needed_node1)

            // root has left child => node2
            drawNode(s_node2, it["node2"].toString(), needed_node2)
            btGenerator.drawEdgeOnLeft(s_node1, s_node2, s_edge1)

            // node2 has left child => node4
            drawNode(s_node4, it["node4"].toString(), needed_node4)
            btGenerator.drawEdgeOnLeft(s_node2, s_node4, s_edge3)

            // node2 has right child => node5
            drawNode(s_node5, it["node5"].toString(), needed_node5)
            btGenerator.drawEdgeOnRight(s_node2, s_node5, s_edge4)

            // right tree
            drawNode(s_node3, it["node3"].toString(), needed_node3)
            btGenerator.drawEdgeOnRight(s_node1, s_node3, s_edge2)
        })

        viewModel.bst.observe(this, {
            correctOrder = arrayOf(s_node2.text, s_node4.text, s_node1.text, s_node5.text, s_node3.text)
        })
    }

    private fun drawRandomBinaryTreeForLeftRotation() {
        viewModel.generateRandomTree()
        viewModel.nodesMap.observe(this, {
            drawNode(s_node1, it["root"].toString(), needed_node1)

            // root has left child => node2
            drawNode(s_node2, it["node2"].toString(), needed_node2)
            btGenerator.drawEdgeOnLeft(s_node1, s_node2, s_edge1)

            // right tree
            drawNode(s_node3, it["node3"].toString(), needed_node3)
            btGenerator.drawEdgeOnRight(s_node1, s_node3, s_edge2)

            // node3 has left child => node6
            drawNode(s_node6, it["node6"].toString(), needed_node4)
            btGenerator.drawEdgeOnLeft(s_node3, s_node6, s_edge5)

            // node2 has right child => node7
            drawNode(s_node7, it["node7"].toString(), needed_node5)
            btGenerator.drawEdgeOnRight(s_node3, s_node7, s_edge6)
        })

        viewModel.bst.observe(this, {
            correctOrder = arrayOf(s_node3.text, s_node1.text, s_node7.text, s_node2.text, s_node6.text)
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
        val args = RightTreeRotationArgs.fromBundle(requireArguments())
        direction = if(args.direction == "right") Direction.RIGHT else Direction.LEFT
        (activity as AppCompatActivity).supportActionBar?.title = "Binary Tree - $direction Rotation"
        viewModel =
            ViewModelProvider(this).get(RightTreeRotationViewModel::class.java)
        return root
    }

    private fun compareResult(currentOrder: Array<CharSequence>): Boolean {
        return currentOrder contentEquals correctOrder
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retryDirection = RightTreeRotationDirections.actionNavRightRotationToRightRotation(direction.toString().lowercase())
        val exerciseDirection = RightTreeRotationDirections.actionNavRightRotationToExerciseCategory()
        refreshTree.setOnClickListener {
            view.findNavController().navigate(retryDirection)
        }

        info.setOnClickListener {
            showInformation(R.string.btRotationInst)
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                if (direction == Direction.LEFT) {
                    drawRandomBinaryTreeForLeftRotation()
                } else if (direction == Direction.RIGHT) {
                    drawRandomBinaryTreeForRightRotation()
                }

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
                    val currentOrder = if (direction == Direction.RIGHT)
                        arrayOf<CharSequence>(
                            textButton1.text,
                            textButton2.text,
                            textButton3.text,
                            textButton6.text,
                            textButton7.text)
                    else
                        arrayOf<CharSequence>(
                            textButton1.text,
                            textButton2.text,
                            textButton3.text,
                            textButton4.text,
                            textButton5.text)

                    val ok = compareResult(currentOrder)

                    val title = if(ok) "Correct" else "Incorrect"
                    val message = if(ok) "Congratulations." else "Your response is not correct, but don't give up! Try again!"
                    viewModel.updateScores(elapsedTime, ok)
                    buildDialog(title, message, retryDirection,exerciseDirection)
                        .setNeutralButton("Result") { dialog, which ->
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
}

