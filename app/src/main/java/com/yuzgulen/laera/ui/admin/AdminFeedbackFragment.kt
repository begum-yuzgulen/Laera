package com.yuzgulen.laera.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuzgulen.laera.FeedbackAdapter
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.models.Feedback
import com.yuzgulen.laera.domain.usecases.GetFeedback
import com.yuzgulen.laera.utils.ICallback
import kotlinx.android.synthetic.main.fragment_admin_feedback.*

class AdminFeedbackFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GetFeedback.getInstance().execute(object : ICallback<List<Feedback>> {
            override fun onCallback(value: List<Feedback>) {
                val adapter = FeedbackAdapter(requireContext(), value)
                userFeedbacks.adapter = adapter
            }

        })
    }
}