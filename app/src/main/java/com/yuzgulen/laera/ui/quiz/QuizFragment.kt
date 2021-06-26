package com.yuzgulen.laera.ui.quiz

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*
import com.yuzgulen.laera.Question

import com.yuzgulen.laera.R
import com.yuzgulen.laera.databinding.QuizFragmentBinding
import kotlinx.android.synthetic.main.quiz_fragment.*
import java.util.*

class QuizFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel
    private lateinit var topic:String
    val database = FirebaseDatabase.getInstance().reference
    var scor : Int = 0
    val rng = Random()
    var generated = ArrayList<Int>()
    fun loadQuestion(myRef: DatabaseReference, i : Int, binding:QuizFragmentBinding, inflater: LayoutInflater ){
        binding.textView3.text = "%d/10".format(i+1)
        myRef.child(generated[i].toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val q = dataSnapshot.getValue(Question::class.java)
                if(i<9) {
                    binding.question.text = q?.question
                    binding.ans1.text = q?.ans1
                    binding.ans2.text = q?.ans2
                    binding.ans3.text = q?.ans3
                    binding.ans4.text = q?.ans4
                    binding.ans1.setOnClickListener {
                        if (q?.ans1 == q?.correct)
                            scor++
                        loadQuestion(myRef, i + 1, binding, inflater)
                    }
                    binding.ans2.setOnClickListener {
                        if (q?.ans2 == q?.correct) {
                            scor++
                        }
                        loadQuestion(myRef, i + 1, binding, inflater)
                    }
                    binding.ans3.setOnClickListener {
                        if (q?.ans3 == q?.correct)
                            scor++
                        loadQuestion(myRef, i + 1, binding, inflater)
                    }
                    binding.ans4.setOnClickListener {
                        if (q?.ans4 == q?.correct)
                            scor++
                        loadQuestion(myRef, i + 1, binding, inflater)
                    }
                    binding.skipButton.setOnClickListener {
                        loadQuestion(myRef, i + 1, binding, inflater)
                    }
                }
                else{
                    MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Quiz Finished")
                    .setMessage("You have finished $topic quiz with a score of $scor/10.")
                    .setPositiveButton("Go to lessons") { _, _ ->
                        view?.findNavController()?.navigate(QuizFragmentDirections.actionQuizzFragmentToHomeFragment())
                    }
                    .setNegativeButton("Try again") { _, _ ->
                        view?.findNavController()?.navigate(QuizFragmentDirections.actionQuizzFragmentToQuizFragment(topic))
                    }.show()
                    return
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "loadQuestion:onCancelled", error.toException())
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
        while (generated.size < 10) {
            val next = rng.nextInt(14)
            if(!generated.contains(next))
                generated.add(next)
        }
        binding.loading.visibility = View.GONE
        binding.quizLayout.visibility = View.VISIBLE
        (activity as AppCompatActivity).supportActionBar?.title = "$topic Quiz"
        val myRef = database.child("questions").child(topic)
        loadQuestion(myRef, 0,binding, inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)

    }

}
