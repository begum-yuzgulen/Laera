package com.yuzgulen.laera.ui.exercise

import android.R.attr.button
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_exercise.*
import android.R.attr.startX
import android.opengl.ETC1.getHeight
import android.view.ViewTreeObserver
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.internal.ViewUtils.addOnGlobalLayoutListener
import com.yuzgulen.laera.ui.home.HomeFragmentDirections


class ExerciseFragment : Fragment() {

    private lateinit var exerciseViewModel: ExerciseViewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exerciseViewModel =
            ViewModelProviders.of(this).get(ExerciseViewModel::class.java)
        val root = inflater.inflate(com.yuzgulen.laera.R.layout.fragment_exercise, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postfix.setOnClickListener{
            view.findNavController().navigate(
                ExerciseFragmentDirections.actionNavExerciseToTraverseBSTExercise()
            )
        }

    }


}