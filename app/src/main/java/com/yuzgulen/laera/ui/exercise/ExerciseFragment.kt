package com.yuzgulen.laera.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yuzgulen.laera.R
import kotlinx.android.synthetic.main.fragment_exercise.*


class ExerciseFragment : Fragment() {

    private lateinit var exerciseViewModel: ExerciseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exerciseViewModel =
            ViewModelProviders.of(this).get(ExerciseViewModel::class.java)
        //(activity as AppCompatActivity).supportActionBar!!.hide()
        return inflater.inflate(R.layout.fragment_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tab_layout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.white))
        tab_layout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        tab_layout.tabTextColors = ContextCompat.getColorStateList(requireContext(), R.color.white)

        val numberOfTabs = 4
        tab_layout.isInlineLabel = true
        val adapter = TabsPagerAdapter(this, numberOfTabs, true)
        tabs_viewpager.adapter = adapter
        tabs_viewpager.isUserInputEnabled = true
        TabLayoutMediator(tab_layout, tabs_viewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Binary Tree Traversal"
                }
                1 -> {
                    tab.text = "Binary Tree Rotations"
                }
                2 -> {
                    tab.text = "Array sorting"
                }
                3 -> {
                    tab.text = "Red Black Trees"
                }

            }
        }.attach()

    }


}