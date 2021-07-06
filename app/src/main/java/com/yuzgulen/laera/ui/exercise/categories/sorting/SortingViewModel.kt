package com.yuzgulen.laera.ui.exercise.categories.sorting

import android.graphics.Color
import android.view.View
import androidx.lifecycle.ViewModel
import com.yuzgulen.laera.databinding.SortingFragmentBinding
import com.yuzgulen.laera.domain.usecases.UpdateScore
import java.util.*
import kotlin.random.Random

class SortingViewModel : ViewModel() {
    fun randomToSort() : MutableList<Int> {
        var array : MutableList<Int> = arrayListOf()
        var n : Int
        var i = 0
        while(i < 5){
            n = Random.nextInt(100)
            if (!array.contains(n)){
                array.add(n)
                i++
            }

        }
        return array
    }
    fun randomArray(toSort: MutableList<Int>) : MutableList<Int> {
        var array : MutableList<Int> = arrayListOf()
        var n : Int
        var i = 0
        while(i<10){
            n = Random.nextInt(100)
            if (!array.contains(n)  && !toSort.contains(n)){
                array.add(n)
                i++
            }
        }
        return array
    }

    fun updateScores(finishTime: String, success: Boolean = true) {
        UpdateScore.getInstance().execute("sorting", "Array Sorting", finishTime, success)
    }

}
