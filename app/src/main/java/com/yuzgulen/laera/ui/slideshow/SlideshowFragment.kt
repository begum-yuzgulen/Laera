package com.yuzgulen.laera.ui.slideshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.yuzgulen.laera.R
import com.yuzgulen.laera.User
import com.yuzgulen.laera.services.Retrofit2Firebase
import com.yuzgulen.laera.services.UserResponse
import com.yuzgulen.laera.services.UserService
import kotlinx.android.synthetic.main.fragment_slideshow.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val retrofit = Retrofit2Firebase.get()
        val service = retrofit.create(UserService::class.java)
        val currentUser = FirebaseAuth.getInstance().currentUser
        val call = service.getCurrentUser(currentUser!!.uid)
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.code() == 200) {
                    Log.d("RESPONSE", currentUser.uid + response.body())
                    val user = response.body()

                    val stringBuilder = "Username: " +
                            user.username +
                            "\n" +
                            "User: " +
                            user.toString()

                    root.text_username!!.text = stringBuilder
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                root.text_username!!.text = t.message
            }
        })
        return root
    }
}