package com.yuzgulen.laera.ui.exercise.categories.sorting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import com.yuzgulen.laera.R
import kotlinx.android.synthetic.main.sorting_result_fragment.*

class SortingResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercise, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        again.setOnClickListener{
            view.findNavController().navigate(
                SortingResultFragmentDirections.actionCongratsFragmentToChooseFragment()
            )
        }
        back.setOnClickListener{
            view.findNavController().navigate(
                SortingResultFragmentDirections.actionCongratsFragmentToTitleFragment()
            )
        }
    }
}
