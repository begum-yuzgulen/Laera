package com.yuzgulen.laera.ui.exercise.categories.rbtree

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuzgulen.laera.algorithms.BinaryTree
import com.yuzgulen.laera.domain.usecases.UpdateScore
import com.yuzgulen.laera.ui.exercise.categories.commons.BinaryTreeGeneration

class RedBlackTreeViewModel : ViewModel() {
    private val _rbTreeNodesMap = MutableLiveData<MutableMap<String, Int>>()
    val rbTreeNodesMap : LiveData<MutableMap<String, Int>> = _rbTreeNodesMap

    private val _bst = MutableLiveData<BinaryTree>()

    fun generateRandomTree() {
        val binaryTree = BinaryTreeGeneration().generateRandomTree()
        _rbTreeNodesMap.value = binaryTree.first!!
        _bst.value = binaryTree.second!!
    }

    fun updateScores(finishTime: String, success: Boolean = true) {
        UpdateScore.getInstance().execute("rb-tree-recolor", "Red Black Tree Recoloring", finishTime, success)
    }
}