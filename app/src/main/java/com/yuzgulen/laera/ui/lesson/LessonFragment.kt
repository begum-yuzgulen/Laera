package com.yuzgulen.laera.ui.lesson

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.GsonBuilder
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.models.ChapterResponse
import com.yuzgulen.laera.aretrofitservices.*
import com.yuzgulen.laera.domain.models.Chapter
import com.yuzgulen.laera.domain.models.Progress
import com.yuzgulen.laera.domain.usecases.GetChapters
import com.yuzgulen.laera.domain.usecases.UpdateProgress
import com.yuzgulen.laera.utils.ICallback
import kotlinx.android.synthetic.main.lesson_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LessonFragment : Fragment() {

    private lateinit var viewModel: LessonViewModel
    private var topic:String = ""
    private var path:String = ""
    private var selectedItem: String = ""
    private var selectedItemId: String = ""
    private var progress: String = ""
    private lateinit var root: View
    private val retrofit = Retrofit2Firebase.get()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private lateinit var chapters: List<Chapter>
    private var nrChapters: Int = 0

    private fun getChapters() {
        GetChapters().execute(selectedItemId, object: ICallback<List<Chapter>> {

            override fun onCallback(value: List<Chapter>) {
                chapters = value
                nrChapters = chapters.size
                Log.e("chapters", chapters.joinToString { ", " })
                lesson(progress)
            }
        })
    }

    private fun lesson(lessonProgress: String){
        var progress = lessonProgress.toInt()
        root.lesson_progress.progress = (progress * 100)/nrChapters
        if(progress < nrChapters) {
            if (chapters[progress].layout == "layout1") {
                root.lesson_layout1.visibility = View.VISIBLE
                root.content_layout2.visibility = View.INVISIBLE
            } else if (chapters[progress].layout == "layout2") {
                root.lesson_layout1.visibility = View.VISIBLE
                root.content_layout2.visibility = View.INVISIBLE
            }
            root.content_layout1.text = chapters[progress].content
            if (progress == nrChapters - 1) {
                root.next.text = "Open quiz"
                root.next.setOnClickListener {
                    requireView().findNavController().navigate(
                        LessonFragmentDirections.actionLessonFragmentToQuizzFragment(selectedItem)
                    )
                }
            } else {
                root.next.setOnClickListener {
                    UpdateProgress().execute(currentUser!!.uid, selectedItemId, progress + 1)
                    progress += 1

                    lesson(progress.toString())

                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root =  inflater.inflate(R.layout.lesson_fragment, container, false)
        val database = FirebaseDatabase.getInstance().reference
        val auth = FirebaseAuth.getInstance()
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
