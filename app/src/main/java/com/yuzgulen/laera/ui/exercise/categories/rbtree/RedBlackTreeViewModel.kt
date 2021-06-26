package com.yuzgulen.laera.ui.exercise.categories.rbtree

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuzgulen.laera.algorithms.BinaryTree
import com.yuzgulen.laera.domain.usecases.UpdateScore
import com.yuzgulen.laera.ui.exercise.categories.commons.BinaryTreeGeneration

class RedBlackTreeViewModel : ViewModel() {
    private val _nodesMap = MutableLiveData<MutableMap<String, Int>>()
    val nodesMap : LiveData<MutableMap<String, Int>> = _nodesMap

    private val _bst = MutableLiveData<BinaryTree>()
    val bst : LiveData<BinaryTree> = _bst
    var addedNodes: MutableSet<Int> = mutableSetOf()

    fun generateRandomTree() {
        val binaryTree = BinaryTreeGeneration().generateRandomTree()
        _nodesMap.value = binaryTree.first!!
        _bst.value = binaryTree.second!!
    }

    fun updateScores(finishTime: String, success: Boolean = true) {
        UpdateScore().execute("rb-tree-recolor", "Red Black Tree Recoloring", finishTime, success)
    }
}