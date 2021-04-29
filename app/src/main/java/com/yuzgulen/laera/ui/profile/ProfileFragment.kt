package com.yuzgulen.laera.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso
import com.yuzgulen.laera.LoginActivity
import com.yuzgulen.laera.R
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var chart: BarChart? = null

    private fun moveToLoginActivity() {

        val i = Intent(activity, LoginActivity::class.java)
        startActivity(i)
        (activity as Activity).overridePendingTransition(0, 0)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        profileViewModel.getUser().observe(this, Observer {
            val user = it
            username.text = user?.username
            sorting.text = "Sorting"
            sortingP.text = user?.progressSorting
            sortingS.text = user?.scoreSorting

            lists.text = "Lists"
            listsP.text = user?.progressLists
            listsS.text = user?.scoreLists

            bst.text = "BST"
            bstP.text = user?.progressBST
            bstS.text = user?.scoreBST

            graphs.text = "Graphs"
            graphsP.text = user?.progressGraphs
            graphsS.text = user?.scoreGraphs

            heaps.text = "Heaps"
            heapsP.text = user?.progressHeaps
            heapsS.text = user?.scoreHeaps

            greedy.text = "Greedy"
            greedyP.text = user?.progressGreedy
            greedyS.text = user?.scoreGreedy


            Picasso.get().load(user?.profilePic).into(profilePic)
        })


        root.signOutButton.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleSignInClient =  GoogleSignIn.getClient(requireActivity().applicationContext, gso)
            profileViewModel.signOut(googleSignInClient)
            moveToLoginActivity()
        }

        chart = root.findViewById(R.id.fragment_groupedbarchart_chart)

        val data = createChartData()
        configureChartAppearance()
        prepareChartData(data)
        return root
    }

    private fun configureChartAppearance() {
        chart!!.setPinchZoom(false)
        chart!!.setDrawBarShadow(false)
        chart!!.setDrawGridBackground(false)

        chart!!.description.isEnabled = false

        val xAxis = chart!!.xAxis
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)

        val leftAxis = chart!!.axisLeft
        leftAxis.setDrawGridLines(false)
        leftAxis.spaceTop = 10f
        leftAxis.axisMinimum = 0f

        chart!!.axisRight.isEnabled = false

        chart!!.xAxis.axisMinimum = 0f
        chart!!.xAxis.axisMaximum = MAX_X_VALUE.toFloat()
    }

    private fun createChartData(): BarData {

        val values1 = ArrayList<BarEntry>()
        val values2 = ArrayList<BarEntry>()

        for (i in 0 until MAX_X_VALUE) {
            values1.add(BarEntry(i.toFloat(), (MIN_Y_VALUE..MAX_Y_VALUE).random().toFloat()))
            values2.add(BarEntry(i.toFloat(), (MIN_Y_VALUE..MAX_Y_VALUE).random().toFloat()))
        }

        val set1 = BarDataSet(values1, GROUP_1_LABEL)
        val set2 = BarDataSet(values2, GROUP_2_LABEL)

        set1.color = ColorTemplate.MATERIAL_COLORS[0]
        set2.color = ColorTemplate.MATERIAL_COLORS[2]

        val dataSets = mutableListOf<BarDataSet>()
        dataSets.add(set1)
        dataSets.add(set2)

        return BarData(mutableListOf(set1, set2) as List<IBarDataSet>?)
    }

    private fun prepareChartData(data: BarData) {
        chart!!.data = data

        chart!!.barData.barWidth = BAR_WIDTH

        val groupSpace = 1f - (BAR_SPACE + BAR_WIDTH) * GROUPS
        chart!!.groupBars(0f, groupSpace, BAR_SPACE)

        chart!!.invalidate()
    }

    companion object {
        private val MAX_X_VALUE = 5
        private val MAX_Y_VALUE = 50
        private val MIN_Y_VALUE = 5
        private val GROUPS = 3
        private val GROUP_1_LABEL = "Successes"
        private val GROUP_2_LABEL = "Failures"
        private val BAR_SPACE = 0.05f
        private val BAR_WIDTH = 0.2f
    }
}