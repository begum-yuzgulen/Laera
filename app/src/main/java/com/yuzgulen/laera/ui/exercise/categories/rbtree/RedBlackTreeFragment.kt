package com.yuzgulen.laera.ui.exercise.categories.rbtree

import android.graphics.Color.BLACK
import android.graphics.Color.RED
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yuzgulen.laera.R
import com.yuzgulen.laera.ui.exercise.categories.ExerciseCategory
import com.yuzgulen.laera.ui.exercise.categories.commons.BinaryTreeGeneration
import com.yuzgulen.laera.utils.Colors
import kotlinx.android.synthetic.main.binary_tree.*
import kotlinx.android.synthetic.main.red_black_tree_fragment.*

class RedBlackTreeFragment : ExerciseCategory() {

    private lateinit var viewModel: RedBlackTreeViewModel
    val btGenerator = BinaryTreeGeneration()
    private val retryDirection = RedBlackTreeFragmentDirections.actionNavRbTreeToRbTree()
    private val exerciseDirection = RedBlackTreeFragmentDirections.actionNavRbTreeToExerciseCategory()
    private lateinit var correctResult : List<Pair<String, Int>>
    private lateinit var case: InsertionCase

    enum class InsertionCase {
        LL, // left-left rotation
        LR, // left-right rotation
        RR, // right-right rotation
        RL // right-left rotation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.red_black_tree_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Red Black Tree - Insertion Cases"
        refreshTree.setOnClickListener {
            view.findNavController().navigate(RedBlackTreeFragmentDirections.actionNavRbTreeToRbTree())
        }
        info.setOnClickListener {
            showInformation(R.string.redBlackTreeInst)
                .setPositiveButton("Show more") { dialog, which ->
                    val inflater = requireActivity().layoutInflater
                        MaterialAlertDialogBuilder(requireContext()).setTitle("Instructions")
                            .setView(inflater.inflate(R.layout.rbtree_tutorial, null)).
                            setNegativeButton("Cancel"){_, _ -> }
                            .setNeutralButton("Open lesson") {_, _ ->
                                requireView().findNavController()
                                    .navigate(RedBlackTreeFragmentDirections.actionRbTreeFragmentToLessonFragment("Red Black Tree",
                                        0.toString(), "redBlackTrees", 2))
                            }.show()
            }.show()
        }
        startTimer(timer, retryDirection, exerciseDirection)
        viewModel = ViewModelProvider(this).get(RedBlackTreeViewModel::class.java)
        case = InsertionCase.values()[(0..3).random()]
        Log.e("Case", case.toString())
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                drawRandomBinaryTree(case)

                btGenerator.setLongClickListeners(listOf(needed_node1, needed_node2,
                    needed_node3, needed_node4))

                val dropButtons = listOf(textButton1, textButton2, textButton3,
                    textButton4, textButton5, textButton6, textButton7)

                btGenerator.setDragListeners(dropButtons, rebuildEdges)

                dropButtons.forEach { b ->
                    b.setOnClickListener {
                        var backgroundColor : Int? = null
                        val background: Drawable = it.getBackground()
                        if (background is ColorDrawable) backgroundColor = background.color
                        if (backgroundColor == RED) it.setBackgroundColor(BLACK)
                        else if (backgroundColor == BLACK) it.setBackgroundColor(RED)
                    }
                }

                submit.setOnClickListener {
                    val ok = checkResult()
                    val title = if(ok) "Correct" else "Incorrect"
                    val message = if(ok) "Congratulations." else "Your response is not correct, but don't give up! Try again!"
                    viewModel.updateScores(elapsedTime, ok)
                    buildDialog(title, message, retryDirection,exerciseDirection)
                        .setNeutralButton("Result") { dialog, which ->

                            val emptyButtons = if(case == InsertionCase.LL || case == InsertionCase.LR)
                                listOf(textButton4, textButton6, textButton7)
                                else listOf(textButton5, textButton6, textButton7)

                            val neededButtons = if(case == InsertionCase.LL || case == InsertionCase.LR)
                                listOf(textButton1, textButton2, textButton3, textButton7)
                            else listOf(textButton1, textButton2, textButton3, textButton4)

                            showCorrectResult(neededButtons, emptyButtons)
                        }
                        .show()

                    cancelTimer()
                }
            }
        })
    }

    val rebuildEdges = {
        btGenerator.drawOrDeleteLeftEdge(textButton1, textButton2, edge1, BLACK)
        btGenerator.drawOrDeleteRightEdge(textButton1, textButton3, edge2, BLACK)
        btGenerator.drawOrDeleteLeftEdge(textButton2, textButton4, edge3, BLACK)
        btGenerator.drawOrDeleteRightEdge(textButton2, textButton5, edge4, BLACK)
        btGenerator.drawOrDeleteLeftEdge(textButton3, textButton6, edge5, BLACK)
        btGenerator.drawOrDeleteRightEdge(textButton3, textButton7, edge6, BLACK)
    }

    private fun drawRandomBinaryTree(case: InsertionCase) {
        viewModel.generateRandomTree()
        viewModel.rbTreeNodesMap.observe(viewLifecycleOwner, {
            drawNode(s_node1, it["root"].toString(), needed_node1, BLACK)

            // root has left child => node2
            if(case == InsertionCase.LL || case == InsertionCase.LR) {
                drawNode(s_node2, it["node2"].toString(), needed_node2, RED)
            }
            else if (case == InsertionCase.RL || case == InsertionCase.RR) {
                drawNode(s_node2, it["node2"].toString(), needed_node2, BLACK)
            }
            btGenerator.drawEdgeOnLeft(s_node1, s_node2, s_edge1, BLACK)

            // right tree
            if (case == InsertionCase.LL || case == InsertionCase.LR) {
                drawNode(s_node3, it["node3"].toString(), needed_node3, BLACK)
            }
            else if (case == InsertionCase.RL || case == InsertionCase.RR) {
                drawNode(s_node3, it["node3"].toString(), needed_node3, RED)
            }
            btGenerator.drawEdgeOnRight(s_node1, s_node3, s_edge2, BLACK)

            when (case) {
                InsertionCase.LL -> {
                    drawNode(s_node4, it["node4"].toString(), needed_node4, RED)
                    btGenerator.drawEdgeOnLeft(s_node2, s_node4, s_edge3, BLACK)
                }
                InsertionCase.LR -> {
                    drawNode(s_node5, it["node5"].toString(), needed_node4, RED)
                    btGenerator.drawEdgeOnRight(s_node2, s_node5, s_edge4, BLACK)
                }
                InsertionCase.RL -> {
                    drawNode(s_node6, it["node6"].toString(), needed_node4, RED)
                    btGenerator.drawEdgeOnLeft(s_node3, s_node6, s_edge5, BLACK)
                }
                InsertionCase.RR -> {
                    drawNode(s_node7, it["node7"].toString(), needed_node4, RED)
                    btGenerator.drawEdgeOnRight(s_node3, s_node7, s_edge6, BLACK)
                }
            }
            getCorrectResult()
        })
    }

    private fun getCorrectResult() {
        correctResult = when(case) {
            InsertionCase.LL -> {
                listOf(
                    Pair(s_node2.text.toString(), BLACK),
                    Pair(s_node4.text.toString(), RED),
                    Pair(s_node1.text.toString(), RED),
                    Pair(s_node3.text.toString(), BLACK)
                )
            }
            InsertionCase.LR -> {
                listOf(
                    Pair(s_node5.text.toString(), BLACK),
                    Pair(s_node2.text.toString(), RED),
                    Pair(s_node1.text.toString(), RED),
                    Pair(s_node3.text.toString(), BLACK)
                )
            }
            InsertionCase.RR -> {
                listOf(
                    Pair(s_node3.text.toString(), BLACK),
                    Pair(s_node1.text.toString(), RED),
                    Pair(s_node7.text.toString(), RED),
                    Pair(s_node2.text.toString(), BLACK)
                )
            }
            InsertionCase.RL -> {
                listOf(
                    Pair(s_node6.text.toString(), BLACK),
                    Pair(s_node1.text.toString(), RED),
                    Pair(s_node3.text.toString(), RED),
                    Pair(s_node2.text.toString(), BLACK)
                )
            }
        }
    }

    private fun getBackground(button: Button) : Int? {
        val background: Drawable = button.getBackground()
        if (background is ColorDrawable)
            return background.color
        return null
    }

    private fun checkResult() : Boolean {
        val nodes: MutableList<Button> = mutableListOf(textButton1, textButton2, textButton3)
        if( case == InsertionCase.LL || case == InsertionCase.LR) {
            if (textButton4.text.isNotEmpty() || textButton5.text.isNotEmpty() || textButton6.text.isNotEmpty()) {
                return false
            } else {
                nodes.add(textButton7)
            }
        } else if (case == InsertionCase.RR || case == InsertionCase.RL) {
            if (textButton5.text.isNotEmpty() || textButton6.text.isNotEmpty() || textButton7.text.isNotEmpty()) {
                return false
            } else {
                nodes.add(textButton4)
            }
        }
        val currentOrder : MutableList<Pair<String, Int?>> = mutableListOf()
        nodes.forEach {
            currentOrder.add(Pair(it.text.toString(), getBackground(it)))
        }
        return currentOrder.toTypedArray() contentDeepEquals  correctResult.toTypedArray()
    }

    fun showCorrectResult(neededNodes: List<Button>, emptyNodes: List<Button>) {
        emptyNodes.forEach {
            it.setBackgroundResource(R.drawable.dash_border)
            it.text = ""
        }

        for (i in correctResult.indices) {
            neededNodes[i].text = correctResult[i].first
            neededNodes[i].setBackgroundColor(correctResult[i].second)
        }
        drawCorrectEdges()

    }

    private fun drawCorrectEdges() {
        btGenerator.drawEdgeOnLeft(textButton1, textButton2, edge1, BLACK)
        btGenerator.drawEdgeOnRight(textButton1, textButton3, edge2, BLACK)
        if (case == InsertionCase.LL || case == InsertionCase.LR) {
            btGenerator.drawEdgeOnRight(textButton3, textButton7, edge6, BLACK)
            btGenerator.drawEdgeOnLeft(textButton2, textButton4, edge3, Colors.get(R.color.transperant))
        }
        if (case == InsertionCase.RR || case == InsertionCase.RL) {
            btGenerator.drawEdgeOnLeft(textButton2, textButton4, edge3, BLACK)
            btGenerator.drawEdgeOnRight(textButton3, textButton7, edge6, Colors.get(R.color.transperant))
        }
        btGenerator.drawEdgeOnLeft(textButton3, textButton6, edge5, Colors.get(R.color.transperant))
        btGenerator.drawEdgeOnRight(textButton2, textButton5, edge4, Colors.get(R.color.transperant))
    }

}