package com.yuzgulen.laera.ui.exercise.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yuzgulen.laera.R
import com.yuzgulen.laera.ui.profile.ChartFragment
import com.yuzgulen.laera.utils.Strings


class TabsPagerAdapter (fragment: Fragment, private var numberOfTabs: Int, private var fromExercise: Boolean) : FragmentStateAdapter(fragment){

    override fun createFragment(position: Int): Fragment {
        val categoryBundle = Bundle()
        val exerciseBundle = Bundle()
        when (position) {
            0 -> {
                categoryBundle.putStringArrayList(
                    "categories",
                    arrayListOf(
                        Strings.get(R.string.preorder),
                        Strings.get(R.string.inorder) ,
                        Strings.get(R.string.postorder)
                    )
                )
                exerciseBundle.putString("exerciseType", Strings.get(R.string.traversalExerciseID))
            }
            1 -> {
                categoryBundle.putStringArrayList(
                    "categories",
                    arrayListOf(
                        Strings.get(R.string.rightRotation),
                        Strings.get(R.string.leftRotation)
                    )
                )
                exerciseBundle.putString("exerciseType", Strings.get(R.string.treeRotationExerciseID))
            }
            2 -> {
                categoryBundle.putStringArrayList(
                    "categories",
                    arrayListOf(
                        Strings.get(R.string.bubbleSort),
                        Strings.get(R.string.insertionSort),
                        Strings.get(R.string.selectionSort)
                    )
                )
                exerciseBundle.putString("exerciseType", Strings.get(R.string.sortingExerciseID))
            }
            3-> {
                categoryBundle.putStringArrayList(
                    "categories",
                    arrayListOf(Strings.get(R.string.redBlackTreeRecoloring))
                )
                exerciseBundle.putString("exerciseType", Strings.get(R.string.listsExerciseID))
            }
            else -> categoryBundle.putStringArrayList("categories", arrayListOf())
        }
        return if (fromExercise) {
            val categoryFragment = CategoryFragment()
            categoryFragment.arguments = categoryBundle
            categoryFragment
        } else {
            val chartFragment = ChartFragment()
            chartFragment.arguments = exerciseBundle
            chartFragment
        }

    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}