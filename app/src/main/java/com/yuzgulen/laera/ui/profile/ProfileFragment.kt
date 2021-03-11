package com.yuzgulen.laera.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.yuzgulen.laera.LoginActivity
import com.yuzgulen.laera.R
import com.yuzgulen.laera.User
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var auth: FirebaseAuth

    private fun moveToLoginActivity() {

        val i = Intent(activity, LoginActivity::class.java)
        startActivity(i)
        (activity as Activity).overridePendingTransition(0, 0)

    }
    private fun signOut(auth: FirebaseAuth, googleSignInClient: GoogleSignInClient){
        auth.signOut()
        googleSignInClient.signOut()
        moveToLoginActivity()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        profileViewModel.text.observe(this, Observer {
//            textView.text = it
//        })

        val database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        var googleSignInClient =  GoogleSignIn.getClient(requireActivity().applicationContext, gso)
        googleSignInClient.signOut()
        val currentUser = auth.currentUser

        if(currentUser != null) {
            database.child("user").child(currentUser.uid).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val user = dataSnapshot.getValue(User::class.java)
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
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
        root.signOutButton.setOnClickListener { signOut(auth, googleSignInClient) }
        return root
    }
}