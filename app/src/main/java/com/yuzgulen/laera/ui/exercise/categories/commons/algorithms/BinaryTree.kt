
package com.yuzgulen.laera.ui.exercise.categories.commons.algorithms

class BinaryTree(var root: Node?) {

    class Node(val value: Int) {
        var parent: Node? = null
        var leftChild: Node? = null
        var rightChild: Node? = null
    }

    fun find(value: Int, node: Node? = root): Node? {
        if (node != null){
            if (node.value == value){
                return node;
            } else {
                var foundNode = find(value, node.leftChild)
                if(foundNode == null) {
                    foundNode = find(value, node.rightChild)
                }
                return foundNode
            }
        } else {
            return null
        }
    }


    fun insertChildAtLeft(value: Int, parent: Int) {
        val parent = find(parent)
        parent?.leftChild = Node(value)

    }

    fun insertChildAtRight(value: Int, parent: Int) {
        val parent = find(parent)
        parent?.rightChild = Node(value)

    }

    fun inOrder(curr: Node? = root, nodes: MutableList<Int> = mutableListOf()): List<Int> {
        if(curr == null)
            return nodes
        inOrder(curr.leftChild, nodes)
        nodes.add(curr.value)
        inOrder(curr.rightChild, nodes)
        return nodes
    }


    fun preOrder(curr: Node? = root, nodes: MutableList<Int> = mutableListOf()): List<Int> {
        if(curr == null)
            return nodes
        nodes.add(curr.value)
        preOrder(curr.leftChild, nodes)
        preOrder(curr.rightChild, nodes)
        return nodes
    }

    fun postOrder(curr: Node? = root, nodes: MutableList<Int> = mutableListOf()): List<Int> {
        if(curr == null)
            return nodes
        postOrder(curr.leftChild, nodes)
        postOrder(curr.rightChild, nodes)
        nodes.add(curr.value)
        return nodes
    }
}