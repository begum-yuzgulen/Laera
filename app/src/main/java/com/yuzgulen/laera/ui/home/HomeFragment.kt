package com.yuzgulen.laera.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.yuzgulen.laera.R
import com.yuzgulen.laera.TopicAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.getAllTopics()
        homeViewModel.topicsList.observe(this, Observer {
            root.loading.visibility = View.GONE

            val adapter = TopicAdapter(it, view!!.findNavController())
            root.gvTopics.adapter = adapter
        })

        return root
    }
}