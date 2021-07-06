package com.yuzgulen.laera.ui.lesson

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.models.Chapter
import com.yuzgulen.laera.domain.usecases.UpdateProgress
import com.yuzgulen.laera.utils.App
import kotlinx.android.synthetic.main.lesson_fragment.view.*


class LessonFragment : Fragment() {

    private lateinit var viewModel: LessonViewModel
    private var topic:String = ""
    private var path:String = ""
    private var selectedItem: String = ""
    private var selectedItemId: String = ""
    private var progress: String = ""
    private lateinit var root: View
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private lateinit var chapters: List<Chapter>
    private var nrChapters: Int = 0

    private fun getChapters() {
        viewModel.getChapters(selectedItemId)
        viewModel.chapters.observe(viewLifecycleOwner, {
            chapters = it
            nrChapters = chapters.size
            lesson((progress.toInt()*nrChapters/100))
        })
    }

    private fun navigateToQuiz() {

        viewModel.checkQuestions(selectedItem)
        viewModel.hasQuestions.observe(viewLifecycleOwner, {
            if (it) {
                requireView().findNavController().navigate(
                    LessonFragmentDirections.actionLessonFragmentToQuizzFragment(selectedItem, selectedItemId)
                )
            } else {
                Toast.makeText(App.instance.applicationContext, "This topic has no questions available!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun lesson(progress: Int) {
        var index = progress
        root.image_layout1.visibility = View.GONE
        root.lesson_progress.progress = index*100/nrChapters
        if(index < nrChapters) {
            root.lesson_layout1.visibility = View.VISIBLE
            root.content_layout2.visibility = View.INVISIBLE

            root.title.text = chapters[index].title
            root.content_layout1.text = chapters[index].content

            if(chapters[index].image != null) {
                val storage = Firebase.storage
                val storageRef = storage.reference
                storageRef.child("lessons/" + selectedItemId + "/" + chapters[index].image).downloadUrl.addOnSuccessListener {
                    Picasso.get().load(it).into(root.image_layout1)
                    root.image_layout1.visibility = View.VISIBLE
                }.addOnFailureListener {
                    Log.e("storage error", it.message.toString())
                }
            }

            if (index == nrChapters - 1) {
                root.next.text = "Open quiz"
                root.next.setOnClickListener {
                    index += 1
                    viewModel.updateProgress(currentUser!!.uid, selectedItemId, selectedItem, index)
                    navigateToQuiz()
                }
            } else {
                root.next.setOnClickListener {
                    index += 1
                    viewModel.updateProgress(currentUser!!.uid, selectedItemId, selectedItem, index)
                    lesson(index)

                }
            }
        } else {
            navigateToQuiz()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root =  inflater.inflate(R.layout.lesson_fragment, container, false)
        viewModel =
            ViewModelProvider(this).get(LessonViewModel::class.java)
        val args = LessonFragmentArgs.fromBundle(requireArguments())
        selectedItem = args.selectedItem
        progress = args.progress
        topic = args.topicId + "%d"
        selectedItemId = args.topicId
        nrChapters = args.nrChapters
        path = "progress" + selectedItemId.replaceFirstChar { c -> c.uppercaseChar() }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getChapters()
    }

}
