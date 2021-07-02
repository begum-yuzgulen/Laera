package com.yuzgulen.laera.utils

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

object TabMenuMediator {
    fun get(tabLayout: TabLayout, tabsViewPager: ViewPager2): TabLayoutMediator {
        return TabLayoutMediator(tabLayout, tabsViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Binary Tree Traversal"
                }
                1 -> {
                    tab.text = "Binary Tree Rotations"
                }
                2 -> {
                    tab.text = "Red Black Trees"
                }
                3 -> {
                    tab.text = "Array sorting"
                }
            }
        }
    }

    const val TAB_COUNT = 4
}