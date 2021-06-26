package com.yuzgulen.laera.ui.exercise.categories.commons

import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import com.yuzgulen.laera.R
import com.yuzgulen.laera.algorithms.BinaryTree
import com.yuzgulen.laera.algorithms.Node
import com.yuzgulen.laera.ui.exercise.CanvasView
import com.yuzgulen.laera.utils.Colors

class BinaryTreeGeneration {

    var addedNodes: MutableSet<Int> = mutableSetOf()

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
}