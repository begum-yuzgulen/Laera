package com.yuzgulen.laera.ui.admin

import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage

class AdminViewModel : ViewModel() {
    fun uploadLessonIcon(image: ByteArray, lessonId: String) {
        val imageStorageRef = FirebaseStorage.getInstance().reference.child("lesson_icons/topic_$lessonId.png")
        imageStorageRef.putBytes(image)
    }

    fun uploadChapterImage(image: ByteArray, lessonId: String, title: String) {
        val imageStorageRef = FirebaseStorage.getInstance().reference.child("lessons/$lessonId" + "_" +"$title.png")
        imageStorageRef.putBytes(image)
    }
}