package com.yuzgulen.laera.ui.lesson

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.GsonBuilder
import com.yuzgulen.laera.R
import com.yuzgulen.laera.models.Chapter
import com.yuzgulen.laera.services.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.app_bar_main.view.*
import kotlinx.android.synthetic.main.lesson_fragment.*
import kotlinx.android.synthetic.main.lesson_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LessonFragment : Fragment() {

    private lateinit var viewModel: LessonViewModel
    private lateinit var myRef: DatabaseReference
    private var topic:String = ""
    private var path:String = ""
    private var selectedItem: String = ""
    private var selectedItemId: String = ""
    private var progress: String = ""
    private lateinit var root: View
    private val retrofit = Retrofit2Firebase.get()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private lateinit var chapters: List<ChapterResponse>
    private var nrChapters: Int = 5

    private fun getChapters() {
//        val userService = retrofit.create(ChapterService::class.java)
//        val progressCall = userService.getLessonChapters(selectedItemId)
//        progressCall.enqueue(object : Callback<List<ChapterResponse>> {
//            override fun onResponse(call: Call<List<ChapterResponse>>, response: Response<List<ChapterResponse>>) {
//                if (response.code() == 200) {
//                    val gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
//
//                    Log.e("no binie", gson.toJson(response.body()).toString())
//                    chapters = response.body()
//                    Log.e("errrrooor", chapters.toString())
//                    root.content_layout1.text = chapters[0].content
//                    lesson(progress)
//                    //nrChapters = chapters.size
//
//                }
//            }
//            override fun onFailure(call: Call<List<ChapterResponse>>, t: Throwable) {
//                Log.e("error", t.message!!);
//                // root.text_username!!.text = t.message
//            }
//        })
        Log.d("SELECTEDITEMID", selectedItemId)
        val service = retrofit.create(ChapterService::class.java)

        val call = service.getLessonChapters(selectedItemId)
        call.enqueue(object : Callback<List<ChapterResponse>> {
            override fun onResponse(call: Call<List<ChapterResponse>>, response: Response<List<ChapterResponse>>) {
                if (response.code() == 200) {
                    val gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
                    Log.e("no binie", gson.toJson(response.body()).toString() + selectedItemId)
                    chapters = response.body()
                    nrChapters = chapters.size
                    lesson(progress)

                }
            }
            override fun onFailure(call: Call<List<ChapterResponse>>, t: Throwable) {
                Log.e("acesterror2", t.message!!);
            }
        })
    }

    private fun lesson(lessonProgress: String){
        var progress = lessonProgress.toInt()
        val identifier = resources.getIdentifier("@android:drawable/presence_online", null, null)
        Log.d("values", progress.toString() + nrChapters.toString())
        if(progress < nrChapters) {
            //if (chapters[progress/20].layout == "layout1") {
                root.lesson_layout1.visibility = View.VISIBLE
              //  root.content_layout2.visibility = View.INVISIBLE
            //}
            root.content_layout1.text = chapters[(progress/20)].content
            root.next.setOnClickListener {
                root.prog1.setImageResource(identifier)
                myRef.setValue(progress + 20)
                progress += 20
                lesson(progress.toString())
            }
        }
//        when ((progress.toInt()/20) + 1) {
//            1-> {
//                root.content.text = getString(requireContext().resources.getIdentifier(topic.format(1), "string", requireContext().packageName))
//                root.next.setOnClickListener {
//                    root.prog1.setImageResource(identifier)
//                    myRef.setValue("20")
//                    lesson("20")
//                }
//            }
//            2-> {
//                root.prog1.setImageResource(identifier)
//                root.content.text = getString(requireContext().resources.getIdentifier(topic.format(2), "string", requireContext().packageName))
//                root.next.setOnClickListener {
//                    root.prog2.setImageResource(identifier)
//                    myRef.setValue("40")
//                    lesson("40")
//                }
//            }
//            3 -> {
//                root.prog1.setImageResource(identifier)
//                root.prog2.setImageResource(identifier)
//                root.content.text = getString(requireContext().resources.getIdentifier(topic.format(3), "string", requireContext().packageName))
//                root.next.setOnClickListener {
//                    root.prog3.setImageResource(identifier)
//                    myRef.setValue("60")
//                    lesson("60")
//                }
//            }
//            4 -> {
//                root.prog1.setImageResource(identifier)
//                root.prog2.setImageResource(identifier)
//                root.prog3.setImageResource(identifier)
//                root.content.text = getString(requireContext().resources.getIdentifier(topic.format(4), "string", requireContext().packageName))
//                root.next.setOnClickListener {
//                    root.prog4.setImageResource(identifier)
//                    myRef.setValue("80")
//
//                    lesson("80")
//                }
//            }
//            5 -> {
//                root.prog1.setImageResource(identifier)
//                root.prog2.setImageResource(identifier)
//                root.prog3.setImageResource(identifier)
//                root.prog4.setImageResource(identifier)
//                root.content.text = getString(requireContext().resources.getIdentifier(topic.format(5), "string", requireContext().packageName))
//                root.next.text = "Take quiz"
//                root.next.setOnClickListener {
//                    root.prog5.setImageResource(identifier)
//                    myRef.setValue("100")
////                    view!!.findNavController().navigate(
////                        LessonFragmentDirections.actionLessonFragmentToQuizzFragment(selectedItem)
////                    )
//                }
//            }
//            6-> {
//                requireView().findNavController().navigate(
//                    LessonFragmentDirections.actionLessonFragmentToQuizzFragment(selectedItem)
//                )
//            }
 //       }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root =  inflater.inflate(R.layout.lesson_fragment, container, false)
        val database = FirebaseDatabase.getInstance().reference
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser!!
        val args = LessonFragmentArgs.fromBundle(requireArguments())
        selectedItem = args.selectedItem
        progress = args.progress
        root.title.text = args.topicId
        topic = args.topicId + "%d"
        selectedItemId = args.topicId
        path = "progress" + topic.replaceFirstChar { c -> c.uppercaseChar() }
        root.title.text = path
//        when(args.selectedItem){
//            "Sorting Algorithms" -> {
//                topic = "sorting%d"
//                path = "progressSorting"
//            }
//            "Graphs" -> {
//                topic = "graphs%d"
//                path = "progressGraphs"
//            }
//            "Lists" -> {
//                topic = "lists%d"
//                path = "progressLists"
//            }
//            "Binary Search Tree" -> {
//                topic = "bst%d"
//                path = "progressBST"
//            }
//            "Greedy Approach" -> {
//                topic = "greedy%d"
//                path = "progressGreedy"
//            }
//            "Heaps" -> {
//                topic = "heaps%d"
//                path = "progressHeaps"
//            }
//        }
        myRef = database.child("user").child(user.uid).child(path)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getChapters()
    }

}
