package com.yuzgulen.laera.ui.exercise.categories.treetraversal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuzgulen.laera.algorithms.BinaryTree
import com.yuzgulen.laera.algorithms.Node
import com.yuzgulen.laera.domain.usecases.UpdateScore

class TreeTraversalViewModel : ViewModel() {
    private val _nodesMap = MutableLiveData<MutableMap<String, Int>>()
    val nodesMap : LiveData<MutableMap<String, Int>> = _nodesMap

    private val _bst = MutableLiveData<BinaryTree>()
    val bst : LiveData<BinaryTree> = _bst

    var addedNodes: MutableSet<Int> = mutableSetOf()

    private fun generateRandomNodeKey() : Int {
        val key = (0..99).random()
        if(!addedNodes.contains(key)) {
            addedNodes.add(key)
            return key
        }
        return generateRandomNodeKey()
    }

    fun generateRandomTree() {
        val root = generateRandomNodeKey()
        val nodeMap = mutableMapOf<String, Int>()
        nodeMap["root"] = root
        val bst = BinaryTree(Node(root))
        // left sub tree
        if ( Math.random() < 0.95) {
            // root has left child => node2
            val node2 = generateRandomNodeKey()
            nodeMap["node2"] = node2
            bst.insertChildAtLeft(node2, root)
            if (Math.random() < 0.85) {
                // node2 has left child => node4
                val node4 = generateRandomNodeKey()
                nodeMap["node4"] = node4
                bst.insertChildAtLeft(node4, node2)
            }
            if (Math.random() < 0.7) {
                // node2 has right child => node3
                val node5 = generateRandomNodeKey()
                nodeMap["node5"] = node5
                bst.insertChildAtRight(node5, node2)
            }
        }
        // right sub tree
        if ( Math.random() < 0.95 ) {
            // root has right child => node3
            val node3 = generateRandomNodeKey()
            nodeMap["node3"] = node3
            bst.insertChildAtRight(node3, root)
            if ( Math.random() < 0.85 ) {
                // node3 has left child => node6
                val node6 = generateRandomNodeKey()
                nodeMap["node6"] = node6
                bst.insertChildAtLeft(node6, node3)
            }
            if ( Math.random() < 0.7 ) {
                // node3 has right child => node7
                val node7 = generateRandomNodeKey()
                nodeMap["node7"] = node7
                bst.insertChildAtRight(node7, node3)
            }
        }

        _nodesMap.value = nodeMap
        _bst.value = bst
    }

    fun updateScores(finishTime: String, success: Boolean = true) {
        UpdateScore.getInstance().execute("tree-traversal", "Binary Tree Traversal", finishTime, success)
    }

}
