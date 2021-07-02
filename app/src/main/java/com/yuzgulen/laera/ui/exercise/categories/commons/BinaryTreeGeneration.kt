package com.yuzgulen.laera.ui.exercise.categories.commons

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.yuzgulen.laera.R
import com.yuzgulen.laera.algorithms.BinaryTree
import com.yuzgulen.laera.algorithms.Node
import com.yuzgulen.laera.utils.Colors
import kotlinx.android.synthetic.main.tree_rotation_fragment.*


class BinaryTreeGeneration {

    var addedNodes: MutableSet<Int> = mutableSetOf()
    lateinit var onDropFunction : () -> Unit
    var backgroundColor: Int? = null

    fun drawOrDeleteLeftEdge(button1: Button, button2: Button, edge: CanvasView, color: Int = Color.BLUE) {
        if (!button1.text.isNullOrEmpty() && !button2.text.isNullOrEmpty()) {
            drawEdgeOnLeft(button1, button2, edge, color)
        } else drawEdgeOnLeft(button1, button2, edge, Colors.get(R.color.transperant))
    }

    fun drawOrDeleteRightEdge(button1: Button, button2: Button, edge: CanvasView, color: Int = Color.BLUE) {
        if (!button1.text.isNullOrEmpty() && !button2.text.isNullOrEmpty()) {
            drawEdgeOnRight(button1, button2, edge, color)
        } else drawEdgeOnLeft(button1, button2, edge, Colors.get(R.color.transperant))
    }

    fun drawEdgeOnLeft(button1: TextView, button2: TextView, canvas: CanvasView, color: Int = Color.BLUE) {
        val startX = button1.left.toFloat()
        val startY = button1.top.toFloat()
        val stopX = button2.left.toFloat()
        val stopY = button2.bottom.toFloat()
        canvas.setCoordinates(startX,startY, stopX,stopY, color)
    }

    fun drawEdgeOnRight(button1: TextView, button2: TextView, canvas: CanvasView, color: Int = Color.BLUE) {
        val startX = button1.right.toFloat()
        val startY = button1.top.toFloat()
        val stopX = button2.right.toFloat()
        val stopY = button2.bottom.toFloat()
        canvas.setCoordinates(startX,startY, stopX,stopY, color)
    }
    private fun generateRandomNodeKey() : Int {
        val key = (0..99).random()
        if(!addedNodes.contains(key)) {
            addedNodes.add(key)
            return key
        }
        return generateRandomNodeKey()
    }

    fun generateRandomTree() : Pair<MutableMap<String, Int>, BinaryTree> {
        val root = generateRandomNodeKey()
        val nodeMap = mutableMapOf<String, Int>()
        nodeMap["root"] = root
        val bst = BinaryTree(Node(root))
        // left sub tree
        // root has left child => node2
        val node2 = generateRandomNodeKey()
        nodeMap["node2"] = node2
        bst.insertChildAtLeft(node2, root)

        // node2 has left child => node4
        val node4 = generateRandomNodeKey()
        nodeMap["node4"] = node4
        bst.insertChildAtLeft(node4, node2)

        // node2 has right child => node3
        val node5 = generateRandomNodeKey()
        nodeMap["node5"] = node5
        bst.insertChildAtRight(node5, node2)

        // right sub tree
        // root has right child => node3
        val node3 = generateRandomNodeKey()
        nodeMap["node3"] = node3
        bst.insertChildAtRight(node3, root)
        // node3 has left child => node6
        val node6 = generateRandomNodeKey()
        nodeMap["node6"] = node6
        bst.insertChildAtLeft(node6, node3)

        // node3 has right child => node7
        val node7 = generateRandomNodeKey()
        nodeMap["node7"] = node7
        bst.insertChildAtRight(node7, node3)

        return Pair(nodeMap, bst)
    }

    private val longClickListener = View.OnLongClickListener { v ->
        val item = ClipData.Item(v.tag as? CharSequence)
        val background: Drawable = v.getBackground()
        if (background is ColorDrawable) backgroundColor = background.color

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

    fun setLongClickListeners(buttons: List<Button>) {
        buttons.forEach { it.setOnLongClickListener(longClickListener) }
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
                if (backgroundColor != null) {
                    receiverView.setBackgroundColor(backgroundColor!!)
                }
                onDropFunction()
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

    fun setDragListeners(textButtons: List<Button>, function: () -> Unit) {
        onDropFunction = function
        textButtons.forEach { it.setOnDragListener(dragListen) }
    }
}