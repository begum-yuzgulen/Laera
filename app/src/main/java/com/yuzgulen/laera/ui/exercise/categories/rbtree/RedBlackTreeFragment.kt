package com.yuzgulen.laera.ui.exercise.categories.rbtree

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuzgulen.laera.R
import com.yuzgulen.laera.ui.exercise.categories.commons.BinaryTreeGeneration
import kotlinx.android.synthetic.main.binary_tree.*
import kotlinx.android.synthetic.main.tree_rotation_fragment.*

class RedBlackTreeFragment : Fragment() {

    companion object {
        fun newInstance() = RedBlackTreeFragment()
    }

    private lateinit var viewModel: RedBlackTreeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.red_black_tree_fragment, container, false)
    }

    private fun drawRandomBinaryTree() {
        viewModel.generateRandomTree()
        viewModel.nodesMap.observe(viewLifecycleOwner, {
            val btGenerator = BinaryTreeGeneration()
            val root = it["root"].toString()
            s_node1.text = root
            s_node1.visibility = View.VISIBLE

            // root has left child => node2
            val node2 = it["node2"].toString()
            s_node2.text = node2
            btGenerator.drawEdgeOnLeft(s_node1, s_node2, s_edge1)
            s_node2.visibility = View.VISIBLE
            // node2 has left child => node4
            val node4 = it["node4"].toString()
            s_node4.text = node4
            btGenerator.drawEdgeOnLeft(s_node2, s_node4, s_edge3)
            s_node4.visibility = View.VISIBLE
            // node2 has right child => node5
            val node5 = it["node5"].toString()
            s_node5.text = node5
            btGenerator.drawEdgeOnRight(s_node2, s_node5, s_edge4)
            s_node5.visibility = View.VISIBLE
            // right tree
            val node3 = it["node3"].toString()
            s_node3.text = node3
            btGenerator.drawEdgeOnRight(s_node1, s_node3, s_edge2)
            s_node3.visibility = View.VISIBLE

            // node3 has left child => node 6
            val node6 = it["node6"].toString()
            s_node6.text = node6
            btGenerator.drawEdgeOnLeft(s_node3, s_node6, s_edge5)
            s_node6.visibility = View.VISIBLE
            
            // node3 has right child => node7
            val node7 = it["node7"].toString()
            s_node7.text = node7
            btGenerator.drawEdgeOnLeft(s_node3, s_node7, s_edge6)
            s_node6.visibility = View.VISIBLE
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RedBlackTreeViewModel::class.java)
        drawRandomBinaryTree()
    }



}