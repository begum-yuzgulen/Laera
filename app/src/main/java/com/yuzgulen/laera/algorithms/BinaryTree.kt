
package com.yuzgulen.laera.algorithms

class BinaryTree(var root: Node?) {

    fun find(value: Int, node: Node? = root): Node? {
        if(node != null){
            if(node.value == value){
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

    fun inOrder(): List<Node> {
        fun inOrderRecursive(curr: Node? = root, nodes: MutableList<Node> = mutableListOf()): List<Node> {
            if(curr == null) return nodes

            inOrderRecursive(curr.leftChild, nodes)
            nodes.add(curr)
            inOrderRecursive(curr.rightChild, nodes)
            return nodes
        }

        return inOrderRecursive()
    }

    fun preOrder(): List<Int> {
        fun preOrderRecursive(curr: Node? = root, nodes: MutableList<Int> = mutableListOf()): List<Int> {
            if(curr == null) return nodes

            nodes.add(curr.value)
            preOrderRecursive(curr.leftChild, nodes)
            preOrderRecursive(curr.rightChild, nodes)
            return nodes
        }

        return preOrderRecursive()
    }

    fun postOrder(): List<Node> {
        fun postOrderRecursive(curr: Node? = root, nodes: MutableList<Node> = mutableListOf()): List<Node> {
            if(curr == null) return nodes

            postOrderRecursive(curr.leftChild, nodes)
            postOrderRecursive(curr.rightChild, nodes)
            nodes.add(curr)
            return nodes
        }
        return postOrderRecursive()
    }


}