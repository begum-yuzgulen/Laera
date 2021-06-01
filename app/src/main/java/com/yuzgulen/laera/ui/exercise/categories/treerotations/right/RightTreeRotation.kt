package com.yuzgulen.laera.ui.exercise.categories.treerotations.right

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

import com.yuzgulen.laera.R
import com.yuzgulen.laera.utils.Strings
import com.yuzgulen.laera.algorithms.BinaryTree
import com.yuzgulen.laera.ui.exercise.DragShadow
import com.yuzgulen.laera.algorithms.Node
import com.yuzgulen.laera.ui.exercise.CanvasView
import kotlinx.android.synthetic.main.right_tree_rotation_fragment.*


class RightTreeRotation : Fragment() {

    private lateinit var viewModel: RightTreeRotationViewModel
    private lateinit var correctOrder: Array<CharSequence>
    private lateinit var database: DatabaseReference
    private lateinit var myRefTries: DatabaseReference
    private lateinit var myRefFailures: DatabaseReference
    var nodes: MutableList<Int> = mutableListOf()
    var addedNodes: MutableSet<Int> = mutableSetOf()
    private lateinit var traversalType: String

    private fun drawOrDeleteLeftEdge(button1: Button, button2: Button, edge: CanvasView) {
        if (!button1.text.isNullOrEmpty() && !button2.text.isNullOrEmpty()) {
            drawEdgeOnLeft(button1, button2, edge)
        } else drawEdgeOnLeft(button1, button2, edge, Color.WHITE)
    }
    private fun drawOrDeleteRightEdge(button1: Button, button2: Button, edge: CanvasView) {
        if (!button1.text.isNullOrEmpty() && !button2.text.isNullOrEmpty()) {
            drawEdgeOnRight(button1, button2, edge)
        } else drawEdgeOnLeft(button1, button2, edge, Color.WHITE)
    }

    private fun rebuildEdges() {
        drawOrDeleteLeftEdge(textButton1, textButton2, edge1)
        drawOrDeleteRightEdge(textButton1, textButton3, edge2)
        drawOrDeleteLeftEdge(textButton2, textButton4, edge3)
        drawOrDeleteRightEdge(textButton2, textButton5, edge4)
        drawOrDeleteLeftEdge(textButton3, textButton6, edge5)
        drawOrDeleteRightEdge(textButton3, textButton7, edge6)
        val currentOrder = arrayOf<CharSequence>(
            textButton1.text,
            textButton2.text,
            textButton3.text,
            textButton6.text,
            textButton7.text)
        current_order.text = currentOrder.joinToString()

    }

    private val dragListen = View.OnDragListener { v, event ->
        val receiverView:Button = v as Button

        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    v.invalidate()
                    true
                } else {
                    false
                }
            }

            DragEvent.ACTION_DRAG_ENTERED -> {
                v.invalidate()
                true
            }

            DragEvent.ACTION_DRAG_LOCATION ->
                true

            DragEvent.ACTION_DROP -> {
                val item: ClipData.Item = event.clipData.getItemAt(0)
                val dragData = item.text
                receiverView.text =  dragData
                rebuildEdges()
                v.invalidate()
                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                v.invalidate()

                // returns true; the value is ignored.
                true
            }


            else -> {
                // An unknown action type was received.
                false
            }
        }
    }

    private val longClickListener = View.OnLongClickListener {v ->
        val item = ClipData.Item(v.tag as? CharSequence)

        val dragData = ClipData(
            v.tag as? CharSequence,
            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
            item)

        val myShadow = DragShadow(v, v.tag as CharSequence )

        // Starts the drag
        v.startDrag(
            dragData,   // the data to be dragged
            myShadow,   // the drag shadow builder
            null,       // no need to use local data
            0           // flags (not currently used, set to 0)
        )
    }


    private fun drawEdgeOnLeft(button1: TextView, button2: TextView, canvas: CanvasView, color: Int = Color.BLUE) {
        val startX = button1.left.toFloat()
        val startY = button1.top.toFloat()
        val stopX = button2.left.toFloat()
        val stopY = button2.bottom.toFloat()
        canvas.setCoordinates(startX,startY, stopX,stopY, color)
    }

    private fun drawEdgeOnRight(button1: TextView, button2: TextView, canvas: CanvasView, color: Int = Color.BLUE) {
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

    private fun drawRandomBinaryTree() {
        val root = generateRandomNodeKey()
        val bst = BinaryTree(Node(root))
        s_node1.text = root.toString()
        s_node1.visibility = View.VISIBLE
        needed_node1.text = root.toString()
        needed_node1.tag = root.toString()
        needed_node1.visibility = View.VISIBLE
        // left sub tree

        // root's left child => node2
        val node2 = generateRandomNodeKey()
        s_node2.text = node2.toString()
        drawEdgeOnLeft(s_node1, s_node2, s_edge1)
        bst.insertChildAtLeft(node2, root)
        s_node2.visibility = View.VISIBLE
        needed_node2.text = node2.toString()
        needed_node2.tag = node2.toString()
        needed_node2.visibility = View.VISIBLE

        // node2' left child => node4
        val node4 = generateRandomNodeKey()
        s_node4.text = node4.toString()
        drawEdgeOnLeft(s_node2, s_node4, s_edge3)
        bst.insertChildAtLeft(node4, node2)
        s_node4.visibility = View.VISIBLE
        needed_node4.text = node4.toString()
        needed_node4.tag = node4.toString()
        needed_node4.visibility = View.VISIBLE

        // node2's right child => node5
        val node5 = generateRandomNodeKey()
        s_node5.text = node5.toString()
        drawEdgeOnRight(s_node2, s_node5, s_edge4)
        bst.insertChildAtRight(node5, node2)
        s_node5.visibility = View.VISIBLE
        needed_node5.text = node5.toString()
        needed_node5.tag = node5.toString()
        needed_node5.visibility = View.VISIBLE

        // right sub tree
        val node3 = generateRandomNodeKey()
        s_node3.text = node3.toString()
        drawEdgeOnRight(s_node1, s_node3, s_edge2)
        bst.insertChildAtRight(node3, root)
        s_node3.visibility = View.VISIBLE
        needed_node3.text = node3.toString()
        needed_node3.tag = node3.toString()
        needed_node3.visibility = View.VISIBLE

        correctOrder = arrayOf(s_node2.text, s_node4.text, s_node1.text, s_node5.text, s_node3.text)
        correct_order.text = correctOrder.joinToString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.right_tree_rotation_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Binary Tree - Right Rotation"
        database = FirebaseDatabase.getInstance().reference
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser!!
        myRefTries = database.child("user").child(user.uid).child("right-rotation").child("tries")
        myRefFailures = database.child("user").child(user.uid).child("right-rotation").child("failures")
        return root
    }

    private fun compareResult(currentOrder: Array<CharSequence>): Boolean {
        return currentOrder contentEquals correctOrder
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshTree.setOnClickListener {
            view.findNavController().navigate(RightTreeRotationDirections.actionNavRightRotationToRightRotation())
        }

        info.setOnClickListener {
            MaterialAlertDialogBuilder(context!!)
                .setTitle("Instructions")
                .setMessage(Strings.get(R.string.loremIpsum))
                .setNegativeButton("Cancel") { dialog, which ->
                    // Respond to negative button press
                }
                .show()
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                drawRandomBinaryTree()

                needed_node1.setOnLongClickListener(longClickListener)
                needed_node2.setOnLongClickListener(longClickListener)
                needed_node3.setOnLongClickListener(longClickListener)
                needed_node4.setOnLongClickListener(longClickListener)
                needed_node5.setOnLongClickListener(longClickListener)

                textButton1.setOnDragListener(dragListen)
                textButton2.setOnDragListener(dragListen)
                textButton3.setOnDragListener(dragListen)
                textButton4.setOnDragListener(dragListen)
                textButton5.setOnDragListener(dragListen)
                textButton6.setOnDragListener(dragListen)
                textButton7.setOnDragListener(dragListen)

                textButton1.setOnClickListener {
                    textButton1.text = null
                    rebuildEdges()
                }
                textButton2.setOnClickListener {
                    textButton2.text = null
                    rebuildEdges()
                }
                textButton3.setOnClickListener {
                    textButton3.text = null
                    rebuildEdges()
                }
                textButton4.setOnClickListener {
                    textButton4.text = null
                    rebuildEdges()
                }
                textButton5.setOnClickListener {
                    textButton5.text = null
                    rebuildEdges()
                }
                textButton6.setOnClickListener {
                    textButton6.text = null
                    rebuildEdges()
                }

                textButton7.setOnClickListener {
                    textButton7.text = null
                    rebuildEdges()
                }

                submit.setOnClickListener {
                    val tryAgain = "Try again"
                    val exercises = "Exercises"
                    val currentOrder = arrayOf<CharSequence>(
                        textButton1.text,
                        textButton2.text,
                        textButton3.text,
                        textButton6.text,
                        textButton7.text)
                    val ok = compareResult(currentOrder)

                    val title = if(ok) "Correct" else "Incorrect"
                    val message = if(ok) "Congratulations." else "Your response is not correct, but don't give up! Try again!"
                    myRefTries.setValue(ServerValue.increment(1))
                    if(!ok) myRefFailures.setValue(ServerValue.increment(1))
                    MaterialAlertDialogBuilder(context!!)
                        .setTitle(title)
                        .setMessage(message)
                        .setNegativeButton(tryAgain) { dialog, which ->
                            // Respond to negative button press
                        }
                        .setPositiveButton(exercises) { dialog, which ->
                            // Respond to positive button press
                        }
                        .show()
                }
            }
        })

    }
}

