package com.yuzgulen.laera.ui.admin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.models.Chapter
import com.yuzgulen.laera.domain.models.Topic
import com.yuzgulen.laera.domain.usecases.AddChapters
import com.yuzgulen.laera.domain.usecases.AddLesson
import kotlinx.android.synthetic.main.chapter_entry_layout.view.*
import kotlinx.android.synthetic.main.fragment_admin_lesson.*
import kotlinx.android.synthetic.main.fragment_admin_lesson.view.*
import java.io.ByteArrayOutputStream


class AdminLessonFragment : Fragment() {

    var nrChapters = 0
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var loaded: Boolean = false
    private lateinit var imageView : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_admin_lesson, container, false)
        imageView = root.lessonIconImage
        root.addChapter.setOnClickListener {
            nrChapters += 1
            val chapterLabel = TextView(context)
            chapterLabel.text = resources.getString(R.string.chapterLabel, nrChapters)
            val chapterView = inflater.inflate(R.layout.chapter_entry_layout, chapters, false)
            chapterView.id = nrChapters
            chapterView.load_image1.setOnClickListener {
                imageView = chapterView.chapterImage
                val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(gallery, pickImage)
            }
            chapters.addView(chapterLabel)
            chapters.addView(chapterView)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val keyListener = View.OnKeyListener { p0, p1, p2 ->
            if(p2.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER){
                // perform action on enter key press
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                lessonTitle.clearFocus()
                lessonTitle.isCursorVisible = false
                true
            } else {
                false
            }
        }

        lessonTitle.setOnKeyListener(keyListener)

        lessonIcon.setOnClickListener {
            imageView = lessonIconImage
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

            startActivityForResult(gallery, pickImage, )
        }

        submitLesson.setOnClickListener {
            val title = lessonTitle.text.toString()
            val lessonId = title.lowercase().replace("\\s".toRegex(), "")
            val lesson = Topic(lessonId, title, "topic_$lessonId", nrChapters, null)

            val lessonChapters : MutableList<Chapter> = mutableListOf()
            for (i in 0 until chapters.childCount) {
                val v = chapters[i]
                if (v is TextView) continue
                if (v is LinearLayout) {
                    var image : ByteArray? = null
                    var upload : String? = null
                    if (v.chapterImage.drawable != null) {
                        val bitmap = (v.chapterImage.drawable as BitmapDrawable).bitmap
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                        image = stream.toByteArray()
                    }

                    if(image != null) {
                        upload = AdminViewModel().uploadChapterImage(image, lessonId, v.chapterTitle.toString())
                    }
                    val chapter = Chapter(v.chapterLayout.checkedRadioButtonId.toString(), (v.chapterTitle as EditText).text.toString(), (v.chapterText1 as EditText).text.toString(), upload)
                    lessonChapters.add(chapter)
                    Log.e("chapter:", chapter.toString())
                }
            }

            AddChapters.getInstance().execute(lessonChapters, lessonId)

            if (loaded) {
                val bitmap = (lessonIconImage.drawable as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                val image = stream.toByteArray()
               AdminViewModel().uploadLessonIcon(image, title.lowercase().replace("\\s".toRegex(), ""))
            } else lesson.icon = "topic_graphs.png"
            AddLesson.getInstance().execute(lesson)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
            loaded = true
        }
    }
}