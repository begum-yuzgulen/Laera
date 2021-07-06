package com.yuzgulen.laera.ui.home

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.yuzgulen.laera.R
import com.yuzgulen.laera.TopicAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var adapter: TopicAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        getTopics()
        (root.topic_search as SearchView).setOnQueryTextListener(object: OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTopics()
    }

    private fun getTopics() {
        GlobalScope.launch(Dispatchers.Default) {
            homeViewModel.getAllTopics()
        }
        homeViewModel.topicsList.observe(viewLifecycleOwner, {
            loading.visibility = View.GONE

            adapter = TopicAdapter(it, requireView().findNavController(), topic_search)
            gvTopics.adapter = adapter

        })
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.removeTopicsList()
        loading.visibility = View.VISIBLE
        getTopics()
    }
}