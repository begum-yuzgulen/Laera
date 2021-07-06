package com.yuzgulen.laera.ui.lesson

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.yuzgulen.laera.domain.models.Chapter
import com.yuzgulen.laera.domain.usecases.GetChapters
import com.yuzgulen.laera.domain.usecases.HasQuestions
import com.yuzgulen.laera.domain.usecases.UpdateProgress
import com.yuzgulen.laera.utils.App
import com.yuzgulen.laera.utils.ICallback

class LessonViewModel : ViewModel() {

    private val _chapters = MutableLiveData<List<Chapter>>()
    val chapters : LiveData<List<Chapter>> = _chapters

    private val _hasQuestions = MutableLiveData<Boolean>()
    val hasQuestions : LiveData<Boolean> = _hasQuestions

    fun getChapters(selectedItemId: String) {
        GetChapters.getInstance().execute(selectedItemId, object: ICallback<List<Chapter>> {
            override fun onCallback(value: List<Chapter>) {
                _chapters.value = value
            }
        })
    }

    fun updateProgress(uid: String, selectedItemId: String, selectedItem: String, index: Int) {
        UpdateProgress.getInstance().execute(uid, selectedItemId, selectedItem, index)
    }

    fun checkQuestions(selectedItem: String) {
        HasQuestions.getInstance().execute(selectedItem, object : ICallback<Boolean>{
            override fun onCallback(value: Boolean) {
                _hasQuestions.value = value
            }

        })
    }

}
