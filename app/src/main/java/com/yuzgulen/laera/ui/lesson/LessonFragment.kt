package com.yuzgulen.laera.ui.lesson

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.models.Chapter
import com.yuzgulen.laera.domain.models.QuizScores
import com.yuzgulen.laera.domain.usecases.GetChapters
import com.yuzgulen.laera.domain.usecases.UpdateProgress
import com.yuzgulen.laera.utils.ICallback
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
        GetChapters().execute(selectedItemId, object: ICallback<List<Chapter>> {

            override fun onCallback(value: List<Chapter>) {
                chapters = value
                nrChapters = chapters.size
                lesson((progress.toInt()*nrChapters/100))
            }
        })
    }

    fun navigateToQuiz() {
        requireView().findNavController().navigate(
            LessonFragmentDirections.actionLessonFragmentToQuizzFragment(selectedItem)
        )
    }

    private fun lesson(progress: Int) {
        var index = progress
        root.lesson_progress.progress = index*100/nrChapters
        if(index < nrChapters) {
            if (chapters[index].layout == "layout1") {
                root.lesson_layout1.visibility = View.VISIBLE
                root.content_layout2.visibility = View.INVISIBLE
            } else if (chapters[index].layout == "layout2") {
                root.lesson_layout1.visibility = View.VISIBLE
                root.content_layout2.visibility = View.INVISIBLE
            }
            root.title.text = chapters[index].title
            root.content_layout1.text = chapters[index].content

            if(chapters[index].image != null) {
                val storage = Firebase.storage
                val storageRef = storage.reference
                storageRef.child("lessons/" + selectedItemId + "/" + chapters[index].image).downloadUrl.addOnSuccessListener {
                    Picasso.get().load(it).into(root.image_layout1)
                }.addOnFailureListener {
                    Log.e("storage error", it.message.toString())
                }
            }

            if (index == nrChapters - 1) {
                root.next.text = "Open quiz"
                root.next.setOnClickListener {
                    index += 1
                    UpdateProgress().execute(currentUser!!.uid, selectedItemId, index)
                    navigateToQuiz()
                }
            } else {
                root.next.setOnClickListener {
                    index += 1
                    UpdateProgress().execute(currentUser!!.uid, selectedItemId, index)
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
        val args = LessonFragmentArgs.fromBundle(requireArguments())
        selectedItem = args.selectedItem
        progress = args.progress
        root.title.text = args.topicId
        topic = args.topicId + "%d"
        selectedItemId = args.topicId
        nrChapters = args.nrChapters
        path = "progress" + selectedItemId.replaceFirstChar { c -> c.uppercaseChar() }
        root.title.text = path

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getChapters()
    }

}
