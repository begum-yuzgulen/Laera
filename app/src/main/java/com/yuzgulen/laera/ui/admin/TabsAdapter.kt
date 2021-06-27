package com.yuzgulen.laera.ui.admin

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsAdapter (fragment: Fragment, private var numberOfTabs: Int) : FragmentStateAdapter(fragment){

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                return AdminLessonFragment()
            }
            1 -> {
                return AdminQuestionFragment()
            }
            2 -> {
                return AdminFeedbackFragment()
            }
            else -> AdminLessonFragment()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}