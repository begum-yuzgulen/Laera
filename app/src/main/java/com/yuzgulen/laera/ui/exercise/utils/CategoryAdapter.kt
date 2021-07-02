package com.yuzgulen.laera.ui.exercise.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.navigation.findNavController
import com.yuzgulen.laera.R
import com.yuzgulen.laera.ui.exercise.ExerciseFragmentDirections
import com.yuzgulen.laera.utils.Strings
import kotlinx.android.synthetic.main.category_entry.view.*

internal class CategoryAdapter(context: Context, private var categoryList: ArrayList<Category>) :
    BaseAdapter() {
    var context: Context? = context

    override fun getCount(): Int {
        return categoryList.size
    }

    override fun getItem(position: Int): String? {
        return categoryList[position].name
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val categ = this.categoryList[position]

        val inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val categoryView = inflator.inflate(R.layout.category_entry, null)
        categoryView.categoryName.text = categ.name!!
        categoryView.categoryName.setOnClickListener {
            when(categ.name!!){
                Strings.get(R.string.preorder) -> {
                    categoryView.findNavController().navigate(
                        ExerciseFragmentDirections.actionNavExerciseToTraverseBSTExercise("preorder")
                    )
                }
                Strings.get(R.string.inorder) -> {
                    categoryView.findNavController().navigate(
                        ExerciseFragmentDirections.actionNavExerciseToTraverseBSTExercise("inorder")
                    )
                }
                Strings.get(R.string.postorder) -> {
                    categoryView.findNavController().navigate(
                        ExerciseFragmentDirections.actionNavExerciseToTraverseBSTExercise("postorder")
                    )
                }
                Strings.get(R.string.bubbleSort) -> {
                    categoryView.findNavController().navigate(
                        ExerciseFragmentDirections.actionNavExerciseToSortingAlgorithms(
                            Strings.get(
                                R.string.bubbleSort
                            )
                        )
                    )
                }
                Strings.get(R.string.insertionSort) -> {
                    categoryView.findNavController().navigate(
                        ExerciseFragmentDirections.actionNavExerciseToSortingAlgorithms(
                            Strings.get(
                                R.string.insertionSort
                            )
                        )
                    )
                }
                Strings.get(R.string.selectionSort) -> {
                    categoryView.findNavController().navigate(
                        ExerciseFragmentDirections.actionNavExerciseToSortingAlgorithms(
                            Strings.get(
                                R.string.selectionSort
                            )
                        )
                    )
                }
                Strings.get(R.string.rightRotation) -> {
                    categoryView.findNavController().navigate(
                        ExerciseFragmentDirections.actionNavExerciseToRightTreeRotation()
                    )
                }
                Strings.get(R.string.redBlackTreeInsertionCases) -> {
                    categoryView.findNavController().navigate(
                        ExerciseFragmentDirections.actionNavExerciseToRedBlackTreeRecoloring()
                    )
                }

            }
        }
        return categoryView
    }
}