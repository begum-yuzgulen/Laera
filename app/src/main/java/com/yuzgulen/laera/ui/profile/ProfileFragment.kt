package com.yuzgulen.laera.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore.setLoggingEnabled
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.yuzgulen.laera.LoginActivity
import com.yuzgulen.laera.R
import com.yuzgulen.laera.domain.models.ExerciseScores
import com.yuzgulen.laera.domain.usecases.GetExerciseScores
import com.yuzgulen.laera.ui.exercise.utils.TabsPagerAdapter
import com.yuzgulen.laera.utils.ICallback
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel


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


        root.signOutButton.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleSignInClient =  GoogleSignIn.getClient(requireActivity().applicationContext, gso)
            profileViewModel.signOut(googleSignInClient)
            moveToLoginActivity()
        }

        profileViewModel.getUser().observe(viewLifecycleOwner, {
            val user = it
            username.text = user?.username
            Picasso.get().load(user?.profilePic).into(profilePic)
        })

        profileViewModel.userQuizScores.observe(viewLifecycleOwner, {
            val adapter = LessonScoreAdapter(requireContext(), it)
            lesson_progress.adapter = adapter
        })

        profileViewModel.getExerciseScores().observe(viewLifecycleOwner, {
            Picasso.get().load(it).into(all_exercises_progress)
        })
        return root
    }

    fun loadExerciseScoresChart() {
        var url = "https://quickchart.io/chart?c={type:'bar',data:{labels:"
        GetExerciseScores().execute(FirebaseAuth.getInstance().currentUser!!.uid, object :
            ICallback<List<ExerciseScores>> {
            override fun onCallback(value: List<ExerciseScores>) {
                val labels: MutableList<String> = mutableListOf()
                val successes: MutableList<Int> = mutableListOf()
                val failures: MutableList<Int> = mutableListOf()
                value.forEach {
                    labels.add("'" + it.title + "'")
                    successes.add(it.tries - it.failures)
                    failures.add(it.failures)

                }
                url += labels.toString() +
                        ", datasets:[{label:'Successes', backgroundColor:'green', data:" +
                        successes.toString() + "}, {label:'Failures', backgroundColor:'red', data:" +
                        failures.toString() + "}]}}"
                Picasso.get().isLoggingEnabled = true
                Picasso.get().load(url).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_exercise).into(all_exercises_progress)
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tab_layout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.white))
        tab_layout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        tab_layout.tabTextColors = ContextCompat.getColorStateList(requireContext(), R.color.white)

        val numberOfTabs = 4
        tab_layout.isInlineLabel = true
        val adapter = TabsPagerAdapter(this, numberOfTabs, false)
        tabs_viewpager.adapter = adapter
        tabs_viewpager.isUserInputEnabled = true
        TabLayoutMediator(tab_layout, tabs_viewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Binary Tree Traversal"
                }
                1 -> {
                    tab.text = "Binary Tree Rotations"
                }
                2 -> {
                    tab.text = "Array sorting"
                }
                3 -> {
                    tab.text = "Linked List"
                }

            }
        }.attach()

    }


}