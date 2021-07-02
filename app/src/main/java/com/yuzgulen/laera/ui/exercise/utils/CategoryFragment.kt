package com.yuzgulen.laera.ui.exercise.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yuzgulen.laera.R
import com.yuzgulen.laera.utils.Strings
import kotlinx.android.synthetic.main.fragment_category.view.*
import java.util.ArrayList


class CategoryFragment : Fragment() {

    private var topicList = ArrayList<Category>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_category, container, false)
        val listOfCategories = requireArguments().getStringArrayList("categories")
        val instructionId = requireArguments().getInt("instruction")
        root.instructions.text = Strings.get(instructionId)

        if (listOfCategories != null && listOfCategories.size > 0) {
            for (categoryName in listOfCategories.toList()) {
                topicList.add(Category(categoryName))
            }
            listOfCategories.clear()
        }
        val adapter = CategoryAdapter(requireActivity().applicationContext, topicList)
        root.gvTopics.adapter = adapter

        return root
    }

}
