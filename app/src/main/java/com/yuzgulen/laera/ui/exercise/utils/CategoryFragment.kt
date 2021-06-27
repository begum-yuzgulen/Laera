package com.yuzgulen.laera.ui.exercise.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yuzgulen.laera.R
import kotlinx.android.synthetic.main.fragment_category.view.*
import java.util.ArrayList


class CategoryFragment : Fragment() {

    private var topicList = ArrayList<Category>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_category, container, false)
        val listOfCategories = requireArguments().getStringArrayList("categories")!!
        root.instructions.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

        if (listOfCategories != null && listOfCategories.size > 0)
            for (categoryName in listOfCategories.toList()) {
                topicList.add(Category(categoryName))
            }

        listOfCategories.clear()
        val adapter = CategoryAdapter(requireActivity().applicationContext, topicList)
        root.gvTopics.adapter = adapter

        return root
    }

}
