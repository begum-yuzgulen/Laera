package com.yuzgulen.laera.ui.exercise.categories.treerotations.right

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar

import com.yuzgulen.laera.R
import com.yuzgulen.laera.algorithms.BinaryTree
import com.yuzgulen.laera.algorithms.Node
import com.yuzgulen.laera.ui.exercise.CanvasView
import kotlinx.android.synthetic.main.right_tree_rotation_fragment.*


class RightTreeRotation : Fragment() {

    private lateinit var viewModel: RightTreeRotationViewModel
    private lateinit var correctOrder: List<Int>
    var nodes: MutableList<Int> = mutableListOf()
    var addedNodes: MutableSet<Int> = mutableSetOf()
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

    private fun generateRandomNodeKey() : Int {
        val key = (0..99).random()
        if(!addedNodes.contains(key)) {
            addedNodes.add(key)
            return key
        }
        return generateRandomNodeKey()
    }

    private fun drawRandomBinaryTree() {
        val root = generateRandomNodeKey()
        val bst = BinaryTree(Node(root))
        s_node1.text = root.toString()
        s_node1.visibility = View.VISIBLE
        needed_node1.text = root.toString()
        needed_node1.visibility = View.VISIBLE
        // left sub tree

        // root's left child => node2
        val node2 = generateRandomNodeKey()
        s_node2.text = node2.toString()
        drawEdgeOnLeft(s_node1, s_node2, s_edge1)
        bst.insertChildAtLeft(node2, root)
        s_node2.visibility = View.VISIBLE
        needed_node2.text = node2.toString()
        needed_node2.visibility = View.VISIBLE

        // node2' left child => node4
        val node4 = generateRandomNodeKey()
        s_node4.text = node4.toString()
        drawEdgeOnLeft(s_node2, s_node4, s_edge3)
        bst.insertChildAtLeft(node4, node2)
        s_node4.visibility = View.VISIBLE
        needed_node4.text = node4.toString()
        needed_node4.visibility = View.VISIBLE

        // node2's right child => node3
        val node5 = generateRandomNodeKey()
        s_node5.text = node5.toString()
        drawEdgeOnRight(s_node2, s_node5, s_edge4)
        bst.insertChildAtRight(node5, node2)
        s_node5.visibility = View.VISIBLE
        needed_node5.text = node5.toString()
        needed_node5.visibility = View.VISIBLE

        // right sub tree
        val node3 = generateRandomNodeKey()
        s_node3.text = node3.toString()
        drawEdgeOnRight(s_node1, s_node3, s_edge2)
        bst.insertChildAtRight(node3, root)
        s_node3.visibility = View.VISIBLE
        needed_node3.text = node3.toString()
        needed_node3.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.right_tree_rotation_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Binary Tree - Right Rotation"

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

