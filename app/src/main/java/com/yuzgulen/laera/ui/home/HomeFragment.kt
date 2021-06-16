package com.yuzgulen.laera.ui.home

import android.os.Bundle
import android.view.*
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.yuzgulen.laera.R
import com.yuzgulen.laera.TopicAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


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
        GlobalScope.launch(Dispatchers.Default) {
            homeViewModel.getAllTopics()
        }
        homeViewModel.topicsList.observe(viewLifecycleOwner, Observer {
            root.loading.visibility = View.GONE

            adapter = TopicAdapter(it, requireView().findNavController())
            root.gvTopics.adapter = adapter

        })
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
}