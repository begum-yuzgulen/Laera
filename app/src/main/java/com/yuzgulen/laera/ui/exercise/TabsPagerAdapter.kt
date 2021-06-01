package com.yuzgulen.laera.ui.exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yuzgulen.laera.R
import com.yuzgulen.laera.utils.Strings


class TabsPagerAdapter (fragment: Fragment, private var numberOfTabs: Int) : FragmentStateAdapter(fragment){

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        when (position) {
            0 -> {
                bundle.putStringArrayList(
                    "categories",
                    arrayListOf(
                        Strings.get(R.string.preorder),
                        Strings.get(R.string.inorder) ,
                        Strings.get(R.string.postorder)
                    )
                )
            }
            1 -> {
                bundle.putStringArrayList(
                    "categories",
                    arrayListOf(
                        Strings.get(R.string.rightRotation),
                        Strings.get(R.string.leftRotation)
                    )
                )
            }
            2 -> {
                bundle.putStringArrayList(
                    "categories",
                    arrayListOf(
                        Strings.get(R.string.bubbleSort),
                        Strings.get(R.string.insertionSort),
                        Strings.get(R.string.selectionSort)
                    )
                )
            }
            3-> {
                bundle.putStringArrayList(
                    "categories",
                    arrayListOf(Strings.get(R.string.linkedListInsertion))
                )
            }
            else -> bundle.putStringArrayList("categories", arrayListOf())
        }
        val categoryFragment = CategoryFragment()
        categoryFragment.arguments = bundle
        return categoryFragment
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}