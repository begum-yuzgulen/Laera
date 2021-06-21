package com.yuzgulen.laera.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarEntry
import com.squareup.picasso.Picasso
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.usecases.GetExerciseFinishTimes
import com.yuzgulen.laera.utils.ICallback
import kotlinx.android.synthetic.main.fragment_chart.*

class ChartFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_chart, container, false)
        val exerciseTypeID = requireArguments().getString("exerciseType")!!
        createChartData(exerciseTypeID)

        return root
    }

    private fun createChartData(exerciseTypeID: String) {
        var url = "https://quickchart.io/chart?c={type:'line',data:{labels:"
        GetExerciseFinishTimes().execute(exerciseTypeID, object : ICallback<List<String>> {
            override fun onCallback(value: List<String>) {
                val labels : MutableList<String> = mutableListOf()
                val times : MutableList<Int> = mutableListOf()
                for (i in value.indices) {
                    labels.add("'T$i'")
                    val spllitedTime: List<String> = value[i].split(":")
                    val minutes = spllitedTime[0].toInt()
                    val seconds = spllitedTime[1].toInt()

                    times.add(minutes*60 + seconds)
                }

                url += labels.toString() +
                        ", datasets:[{label:'Finish times (in seconds)', data:"  +
                        times.toString() + "}]}}"
                Log.e("url is:", url)
                Picasso.get().load(url).into(finishTimeChart)
            }

        })

    }


}