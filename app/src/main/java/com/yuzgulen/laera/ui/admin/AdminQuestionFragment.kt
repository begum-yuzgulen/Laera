package com.yuzgulen.laera.ui.admin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.models.Question
import com.yuzgulen.laera.domain.models.Topic
import com.yuzgulen.laera.domain.usecases.AddQuestion
import com.yuzgulen.laera.domain.usecases.GetTopics
import com.yuzgulen.laera.utils.ICallback
import kotlinx.android.synthetic.main.fragment_admin_lesson.*
import kotlinx.android.synthetic.main.fragment_admin_question.*

class AdminQuestionFragment : Fragment() {

    var selectedTopic : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner = Spinner(requireContext())
        spinner.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        spinner.setPadding(20,20,20,20)

        spinnerLayout.addView(spinner)

        val optionButtons = listOf(option1, option2, option3, option4)

        val keyListener = View.OnKeyListener { p0, p1, p2 ->
            if(p2.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER){
                // perform action on enter key press
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                optionButtons.forEach {
                    it.clearFocus()
                    it.isCursorVisible = false
                }
                true
            } else {
                false
            }
        }

        optionButtons.forEach {
            it.setOnKeyListener(keyListener)
        }

        val correctButtons = listOf(correct1, correct2, correct3, correct4)

        val clickListener = View.OnClickListener {
            correctButtons.forEach { b ->
                b.isChecked = false
            }
            (it as CheckBox).isChecked = true
        }

        correctButtons.forEach {
            it.setOnClickListener(clickListener)
        }


        GetTopics.getInstance().execute(object : ICallback<List<Topic>> {
            override fun onCallback(value: List<Topic>) {
                val topicNames : MutableList<String> = mutableListOf()
                value.forEach { topicNames.add(it.title.toString()) }
                val adapter = ArrayAdapter(requireContext(),
                    android.R.layout.simple_spinner_dropdown_item, topicNames)
                spinner.adapter = adapter

                spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>,
                                                view: View, position: Int, id: Long) {
                        selectedTopic = value[position].title.toString()
                        Log.e("selectedTopic", selectedTopic)

                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }
            }
        })

        submitQuestion.setOnClickListener {
            var correctValue = ""
            val question = questionText.text.toString()
            val ans1 = option1.text.toString()
            val ans2 = option2.text.toString()
            val ans3 = option3.text.toString()
            val ans4 = option4.text.toString()
            if (ans1 == "" || ans2 == "" || ans3 == "" || ans4 == "") {
                Toast.makeText(requireContext(), "Please provide all 4 options.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (correct1.isChecked)  correctValue = ans1
            if (correct2.isChecked)  correctValue = ans2
            if (correct3.isChecked)  correctValue = ans3
            if (correct4.isChecked)  correctValue = ans4

            if(correctValue == "") {
                Toast.makeText(requireContext(), "Please check the correct option.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            AddQuestion.getInstance().execute(selectedTopic, Question(question, ans1, ans2, ans3, ans4, correctValue))
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Add Question")
                .setMessage("Question was added.")
                .setPositiveButton("Add another") {dialog, which ->
                    view.findNavController().navigate(AdminQuestionFragmentDirections.actionNavAdminQuizToAdminQuiz())
                }
                .setNegativeButton("Go back") { dialog, which ->
                    view.findNavController().navigate(AdminQuestionFragmentDirections.actionNavAdminQuizToAdminFragment())
                }
                .show()
        }

    }
}