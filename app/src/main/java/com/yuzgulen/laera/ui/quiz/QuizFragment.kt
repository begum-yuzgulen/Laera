package com.yuzgulen.laera.ui.quiz

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.firebase.database.*
import com.yuzgulen.laera.Question

import com.yuzgulen.laera.R
import com.yuzgulen.laera.databinding.QuizFragmentBinding
import kotlinx.android.synthetic.main.quiz_fragment.*
import java.util.*

class QuizFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel
    private lateinit var topic:String
    val database = FirebaseDatabase.getInstance()
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
                    view?.findNavController()?.navigate(QuizFragmentDirections.actionQuizzFragmentToWinFragment(scor,topic))
                    return
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        val binding = DataBindingUtil.inflate<QuizFragmentBinding>(inflater,R.layout.quiz_fragment,container,false)
        val args = QuizFragmentArgs.fromBundle(arguments!!)
        binding.qCategory.text = args.selectedItem
        topic = args.selectedItem
        while (generated.size < 10) {
            val next = rng.nextInt(14)
            if(!generated.contains(next))
                generated.add(next)
        }
        val myRef = database.getReference(topic)
        loadQuestion(myRef, 0,binding, inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)
        // TODO: Use the ViewModel
    }

}