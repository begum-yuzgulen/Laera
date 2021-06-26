package com.yuzgulen.laera.ui.exercise.categories.treerotations.right

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yuzgulen.laera.R
import com.yuzgulen.laera.utils.Strings
import com.yuzgulen.laera.ui.exercise.DragShadow
import com.yuzgulen.laera.ui.exercise.CanvasView
import android.os.CountDownTimer
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
import java.util.concurrent.TimeUnit


class RightTreeRotation : ExerciseCategory() {

    private lateinit var viewModel: RightTreeRotationViewModel
    private lateinit var correctOrder: Array<CharSequence>

    private fun drawOrDeleteLeftEdge(button1: Button, button2: Button, edge: CanvasView) {
        if (!button1.text.isNullOrEmpty() && !button2.text.isNullOrEmpty()) {
            drawEdgeOnLeft(button1, button2, edge)
        } else drawEdgeOnLeft(button1, button2, edge, Colors.get(R.color.transperant))
    }

    private fun drawOrDeleteRightEdge(button1: Button, button2: Button, edge: CanvasView) {
        if (!button1.text.isNullOrEmpty() && !button2.text.isNullOrEmpty()) {
            drawEdgeOnRight(button1, button2, edge)
        } else drawEdgeOnLeft(button1, button2, edge, Colors.get(R.color.transperant))
    }

    private fun rebuildEdges() {
        drawOrDeleteLeftEdge(textButton1, textButton2, edge1)
        drawOrDeleteRightEdge(textButton1, textButton3, edge2)
        drawOrDeleteLeftEdge(textButton2, textButton4, edge3)
        drawOrDeleteRightEdge(textButton2, textButton5, edge4)
        drawOrDeleteLeftEdge(textButton3, textButton6, edge5)
        drawOrDeleteRightEdge(textButton3, textButton7, edge6)
        val currentOrder = arrayOf<CharSequence>(
            textButton1.text,
            textButton2.text,
            textButton3.text,
            textButton6.text,
            textButton7.text)
        current_order.text = currentOrder.joinToString()
    }

    private val dragListen = View.OnDragListener { v, event ->
        val receiverView:Button = v as Button

        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    v.invalidate()
                    true
                } else {
                    false
                }
            }

            DragEvent.ACTION_DRAG_ENTERED -> {
                invalidate(v)
            }

            DragEvent.ACTION_DRAG_LOCATION ->
                true

            DragEvent.ACTION_DROP -> {
                val item: ClipData.Item = event.clipData.getItemAt(0)
                val dragData = item.text
                receiverView.text =  dragData
                rebuildEdges()
                invalidate(v)
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                invalidate(v)
            }


            else -> {
                false
            }
        }
    }

    fun invalidate(v: View) : Boolean {
        v.invalidate()
        return true
    }

    private val longClickListener = View.OnLongClickListener {v ->
        val item = ClipData.Item(v.tag as? CharSequence)

        val dragData = ClipData(
            v.tag as? CharSequence,
            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
            item)

        val myShadow = DragShadow(v, v.tag as CharSequence )

        // Starts the drag
        v.startDrag(
            dragData,
            myShadow,
            null,
            0
        )
    }


    private fun drawEdgeOnLeft(button1: TextView, button2: TextView, canvas: CanvasView, color: Int = Color.BLUE) {
        val startX = button1.left.toFloat()
        val startY = button1.top.toFloat()
        val stopX = button2.left.toFloat()
        val stopY = button2.bottom.toFloat()
        canvas.setCoordinates(startX,startY, stopX,stopY, color)
    }

    private fun drawEdgeOnRight(button1: TextView, button2: TextView, canvas: CanvasView, color: Int = Color.BLUE) {
        val startX = button1.right.toFloat()
        val startY = button1.top.toFloat()
        val stopX = button2.right.toFloat()
        val stopY = button2.bottom.toFloat()
        canvas.setCoordinates(startX,startY, stopX,stopY, color)
    }

    private fun drawRandomBinaryTree() {
        viewModel.generateRandomTree()
        val btGenerator = BinaryTreeGeneration()
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
        drawEdgeOnLeft(textButton1, textButton2, edge1)
        drawEdgeOnRight(textButton1, textButton3, edge2)
        drawEdgeOnLeft(textButton3, textButton6, edge5)
        drawEdgeOnRight(textButton3, textButton7, edge6)
        drawEdgeOnLeft(textButton2, textButton4, edge3, Colors.get(R.color.transperant))
        drawEdgeOnRight(textButton2, textButton5, edge4, Colors.get(R.color.transperant))

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

    fun setLongClickListeners() {
        needed_node1.setOnLongClickListener(longClickListener)
        needed_node2.setOnLongClickListener(longClickListener)
        needed_node3.setOnLongClickListener(longClickListener)
        needed_node4.setOnLongClickListener(longClickListener)
        needed_node5.setOnLongClickListener(longClickListener)
    }

    fun setDragListeners() {
        textButton1.setOnDragListener(dragListen)
        textButton2.setOnDragListener(dragListen)
        textButton3.setOnDragListener(dragListen)
        textButton4.setOnDragListener(dragListen)
        textButton5.setOnDragListener(dragListen)
        textButton6.setOnDragListener(dragListen)
        textButton7.setOnDragListener(dragListen)
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
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Instructions")
                .setMessage(Strings.get(R.string.loremIpsum))
                .setNegativeButton("Cancel") { dialog, which ->
                    // Respond to negative button press
                }
                .show()
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                drawRandomBinaryTree()
                startTimer(timer, retryDirection, exerciseDirection)

                setLongClickListeners()
                setDragListeners()

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

