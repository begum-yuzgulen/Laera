package com.yuzgulen.laera.ui.quiz

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.yuzgulen.laera.R
import com.yuzgulen.laera.databinding.QuizFragmentBinding
import com.yuzgulen.laera.domain.usecases.HasQuestions
import com.yuzgulen.laera.utils.App
import com.yuzgulen.laera.utils.ICallback
import java.util.*

class QuizFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel
    private lateinit var topic: String
    private lateinit var topicId: String
    var scor : Int = 0
    val rng = Random()
    var generated = ArrayList<Int>()
    fun loadQuestion(i : Int, binding:QuizFragmentBinding, inflater: LayoutInflater ){
        binding.textView3.text = "%d/10".format(i+1)
        viewModel.getQuestion(topic, generated[i])
        viewModel.question.observe(viewLifecycleOwner, { q ->
            if(i<9) {
                binding.question.text = q?.question
                binding.ans1.text = q?.ans1
                binding.ans2.text = q?.ans2
                binding.ans3.text = q?.ans3
                binding.ans4.text = q?.ans4
                binding.ans1.setOnClickListener {
                    if (q?.ans1 == q?.correct)
                        scor++
                    loadQuestion(i + 1, binding, inflater)
                }
                binding.ans2.setOnClickListener {
                    if (q?.ans2 == q?.correct) {
                        scor++
                    }
                    loadQuestion(i + 1, binding, inflater)
                }
                binding.ans3.setOnClickListener {
                    if (q?.ans3 == q?.correct)
                        scor++
                    loadQuestion(i + 1, binding, inflater)
                }
                binding.ans4.setOnClickListener {
                    if (q?.ans4 == q?.correct)
                        scor++
                    loadQuestion(i + 1, binding, inflater)
                }
                binding.skipButton.setOnClickListener {
                    loadQuestion(i + 1, binding, inflater)
                }
            }
            else {
                viewModel.addQuizScore(topicId, topic, scor)
                quizFinishedDialog()
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        val binding = DataBindingUtil.inflate<QuizFragmentBinding>(inflater,R.layout.quiz_fragment,container,false)

        val args = QuizFragmentArgs.fromBundle(requireArguments())
        binding.qCategory.text = args.selectedItem
        topic = args.selectedItem
        topicId = args.topicId
        while (generated.size < 10) {
            val next = rng.nextInt(14)
            if(!generated.contains(next))
                generated.add(next)
        }
        binding.loading.visibility = View.GONE
        binding.quizLayout.visibility = View.VISIBLE
        (activity as AppCompatActivity).supportActionBar?.title = "$topic Quiz"
        HasQuestions.getInstance().execute(topic, object :
            ICallback<Boolean> {
            override fun onCallback(value: Boolean) {
                if (value) {
                    loadQuestion(0,binding, inflater)
                } else {
                    Toast.makeText(App.instance.applicationContext, "This topic has no questions available!", Toast.LENGTH_LONG).show()
                }
            }

        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)

    }

    private fun quizFinishedDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Quiz Finished")
            .setMessage("You have finished $topic quiz with a score of $scor/10.")
            .setPositiveButton("Go to lessons") { dialog, _ ->
                dialog.dismiss()
                view?.findNavController()?.navigate(QuizFragmentDirections.actionQuizzFragmentToHomeFragment())

            }
            .setNegativeButton("Try again") { dialog, _ ->
                dialog.dismiss()
                view?.findNavController()?.navigate(QuizFragmentDirections.actionQuizzFragmentToQuizFragment(topic, topicId))
            }.show()
    }

}
