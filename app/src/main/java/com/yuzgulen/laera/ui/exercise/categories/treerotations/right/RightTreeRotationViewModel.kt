package com.yuzgulen.laera.ui.exercise.categories.treerotations.right

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.yuzgulen.laera.algorithms.BinaryTree
import com.yuzgulen.laera.algorithms.Node

class RightTreeRotationViewModel : ViewModel() {
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


        _nodesMap.value = nodeMap
        _bst.value = bst
    }

    fun updateScores(finishTime: String, success: Boolean = true) {
        val database = FirebaseDatabase.getInstance().reference
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser!!
        val myRefTries = database.child("user").child(user.uid).child("right-rotation").child("tries")
        val myRefTimes = database.child("user").child(user.uid).child("right-rotation").child("times")
        val myRefFailures = database.child("user").child(user.uid).child("right-rotation").child("failures")
        myRefTries.setValue(ServerValue.increment(1))
        val newTime = myRefTimes.push()
        newTime.child("time").setValue(finishTime)
        newTime.child("success").setValue(success)
        if(!success) myRefFailures.setValue(ServerValue.increment(1))
    }
}
