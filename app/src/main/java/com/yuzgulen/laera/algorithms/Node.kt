package com.yuzgulen.laera.algorithms

import kotlin.math.max

/**
 * A single node in the tree.
 *
 * @param value value of the node
 *
 * @author Dominik Hoftych
 */
class Node(val value: Int) {

    /**
     * Reference to the parent node, null if this node is root
     */
    var parent: Node? = null

    /**
     * Reference to left child
     */
    var leftChild: Node? = null

    /**
     * Reference to right child
     */
    var rightChild: Node? = null

    /**
     * Depth of this node, i.e. number of edges along the path from root to this node
     */
    val depth: Int
        get() = when(parent) {
            null -> 0
            else -> parent!!.depth + 1
        }

    /**
     * Height of this node, i.e. number of edges along the "downwards" path
     * from this node to the furthest leaf. Root holds the depth of the whole tree.
     */
    val height: Int
        get() = max(leftChild?.height ?: 0, rightChild?.height ?: 0) + 1

    /**
     * Type of this node based on the number of children
     */
    val type: NodeType
        get() = when {
            leftChild == null && rightChild == null -> NodeType.LEAF
            leftChild != null && rightChild != null -> NodeType.DOUBLE_CHILDED
            else -> NodeType.SINGLE_CHILDED
        }

    /**
     * Returns true if the node is a left child
     */
    val isLeftChild: Boolean
        get() = this.parent != null && this.value <= this.parent!!.value

    /**
     * Creates new node with given value and parent.
     *
     * @param value value of the node
     * @param parent reference to parent node
     */
    constructor(value: Int, parent: Node) : this(value) {
        this.parent = parent
        if(value < parent.value) parent.leftChild = this else parent.rightChild = this
    }


    override fun toString(): String {
        return "Node(value=$value, parent=${parent?.value}, left=${leftChild?.value}, right=${rightChild?.value}, depth=$depth, height=$height)"
    }

    /**
     * Type of the node based on the number of children.
     */
    enum class NodeType {
        LEAF, SINGLE_CHILDED, DOUBLE_CHILDED
    }
}