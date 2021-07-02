package com.yuzgulen.laera.ui.admin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.yuzgulen.laera.R
import kotlinx.android.synthetic.main.fragment_exercise.*

class AdminFragment : Fragment() {

    companion object {
        fun newInstance() = AdminFragment()
    }

    private lateinit var viewModel: AdminViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.admin_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tab_layout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.white))
        tab_layout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        tab_layout.tabTextColors = ContextCompat.getColorStateList(requireContext(), R.color.white)
        val numberOfTabs = 3
        tab_layout.isInlineLabel = true
        val adapter = TabsAdapter(this, numberOfTabs)
        tabs_viewpager.adapter = adapter
        tabs_viewpager.isUserInputEnabled = true
        TabLayoutMediator(tab_layout, tabs_viewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "LESSONS"
                }
                1 -> {
                    tab.text = "QUESTIONS"
                }
                2-> {
                    tab.text = "FEEDBACKS"
                }

            }
        }.attach()
    }
}