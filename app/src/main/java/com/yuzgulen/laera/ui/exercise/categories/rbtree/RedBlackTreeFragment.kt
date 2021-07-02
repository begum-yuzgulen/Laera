package com.yuzgulen.laera.ui.exercise.categories.rbtree

import android.graphics.Color
import android.graphics.Color.BLACK
import android.graphics.Color.RED
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.yuzgulen.laera.R
import com.yuzgulen.laera.ui.exercise.categories.ExerciseCategory
import com.yuzgulen.laera.ui.exercise.categories.commons.BinaryTreeGeneration
import com.yuzgulen.laera.utils.Colors
import kotlinx.android.synthetic.main.binary_tree.*
import kotlinx.android.synthetic.main.red_black_tree_fragment.*
import kotlinx.android.synthetic.main.red_black_tree_fragment.edge1
import kotlinx.android.synthetic.main.red_black_tree_fragment.edge2
import kotlinx.android.synthetic.main.red_black_tree_fragment.edge3
import kotlinx.android.synthetic.main.red_black_tree_fragment.edge4
import kotlinx.android.synthetic.main.red_black_tree_fragment.edge5
import kotlinx.android.synthetic.main.red_black_tree_fragment.edge6
import kotlinx.android.synthetic.main.red_black_tree_fragment.needed_node1
import kotlinx.android.synthetic.main.red_black_tree_fragment.needed_node2
import kotlinx.android.synthetic.main.red_black_tree_fragment.needed_node3
import kotlinx.android.synthetic.main.red_black_tree_fragment.needed_node4
import kotlinx.android.synthetic.main.red_black_tree_fragment.refreshTree
import kotlinx.android.synthetic.main.red_black_tree_fragment.submit
import kotlinx.android.synthetic.main.red_black_tree_fragment.textButton1
import kotlinx.android.synthetic.main.red_black_tree_fragment.textButton2
import kotlinx.android.synthetic.main.red_black_tree_fragment.textButton3
import kotlinx.android.synthetic.main.red_black_tree_fragment.textButton4
import kotlinx.android.synthetic.main.red_black_tree_fragment.textButton5
import kotlinx.android.synthetic.main.red_black_tree_fragment.textButton6
import kotlinx.android.synthetic.main.red_black_tree_fragment.textButton7
import kotlinx.android.synthetic.main.red_black_tree_fragment.timer
import kotlinx.android.synthetic.main.tree_rotation_fragment.*

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
                        .setNeutralButton("Show result") { dialog, which ->

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
        viewModel.nodesMap.observe(viewLifecycleOwner, {
            val root = it["root"].toString()
            s_node1.text = root
            s_node1.visibility = View.VISIBLE
            s_node1.setBackgroundColor(BLACK)
            needed_node1.text = root
            needed_node1.tag = root
            needed_node1.visibility = View.VISIBLE
            needed_node1.setBackgroundColor(BLACK)

            // root has left child => node2
            val node2 = it["node2"].toString()
            s_node2.text = node2
            btGenerator.drawEdgeOnLeft(s_node1, s_node2, s_edge1, BLACK)
            s_node2.visibility = View.VISIBLE
            needed_node2.text = node2
            needed_node2.tag = node2
            needed_node2.visibility = View.VISIBLE
            if(case == InsertionCase.LL || case == InsertionCase.LR) {
                s_node2.setBackgroundColor(RED)
                needed_node2.setBackgroundColor(RED)
            }
            else if (case == InsertionCase.RL || case == InsertionCase.RR) {
                s_node2.setBackgroundColor(BLACK)
                needed_node2.setBackgroundColor(BLACK)
            }

            // right tree
            val node3 = it["node3"].toString()
            s_node3.text = node3
            btGenerator.drawEdgeOnRight(s_node1, s_node3, s_edge2, BLACK)
            s_node3.visibility = View.VISIBLE
            needed_node3.text = node3
            needed_node3.tag = node3
            needed_node3.visibility = View.VISIBLE

            if(case == InsertionCase.LL || case == InsertionCase.LR) {
                s_node3.setBackgroundColor(BLACK)
                needed_node3.setBackgroundColor(BLACK)
            }
            else if (case == InsertionCase.RL || case == InsertionCase.RR) {
                s_node3.setBackgroundColor(RED)
                needed_node3.setBackgroundColor(RED)
            }

            when (case) {
                InsertionCase.LL -> {
                    val node4 = it["node4"].toString()
                    s_node4.text = node4
                    btGenerator.drawEdgeOnLeft(s_node2, s_node4, s_edge3, BLACK)
                    s_node4.visibility = View.VISIBLE
                    s_node4.setBackgroundColor(RED)
                    needed_node4.text = node4
                    needed_node4.tag = node4
                    needed_node4.visibility = View.VISIBLE
                    needed_node4.setBackgroundColor(RED)

                }
                InsertionCase.LR -> {
                    val node5 = it["node5"].toString()
                    s_node5.text = node5
                    btGenerator.drawEdgeOnRight(s_node2, s_node5, s_edge4, BLACK)
                    s_node5.visibility = View.VISIBLE
                    s_node5.setBackgroundColor(RED)
                    needed_node4.text = node5
                    needed_node4.tag = node5
                    needed_node4.visibility = View.VISIBLE
                    needed_node4.setBackgroundColor(RED)
                }
                InsertionCase.RL -> {
                    val node6 = it["node6"].toString()
                    s_node6.text = node6
                    btGenerator.drawEdgeOnLeft(s_node3, s_node6, s_edge5, BLACK)
                    s_node6.visibility = View.VISIBLE
                    s_node6.setBackgroundColor(RED)
                    needed_node4.text = node6
                    needed_node4.tag = node6
                    needed_node4.visibility = View.VISIBLE
                    needed_node4.setBackgroundColor(RED)
                }
                InsertionCase.RR -> {
                    val node7 = it["node7"].toString()
                    s_node7.text = node7
                    btGenerator.drawEdgeOnRight(s_node3, s_node7, s_edge6, BLACK)
                    s_node7.visibility = View.VISIBLE
                    s_node7.setBackgroundColor(RED)
                    needed_node4.text = node7
                    needed_node4.tag = node7
                    needed_node4.visibility = View.VISIBLE
                    needed_node4.setBackgroundColor(RED)
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
        Log.e("currentOrder", currentOrder.toString())
        Log.e("correctResu;t", correctResult.toString())
        return currentOrder.toTypedArray() contentDeepEquals  correctResult.toTypedArray()
    }

    fun showCorrectResult(neededNodes: List<Button>, emptyNodes: List<Button>) {
        emptyNodes.forEach {
            it.setBackgroundResource(R.drawable.dash_border)
            it.text = ""
        }

        for (i in correctResult.indices) {
            Log.e("correcttext", correctResult[i].first + correctResult[i].second.toString())
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