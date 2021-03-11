package com.yuzgulen.laera.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.graphics.Canvas
import android.util.Log
import android.widget.RelativeLayout
import com.yuzgulen.laera.R
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery.view.*


class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        val firstLeft = root.textButton7.left
        val firstRight = root.textButton7.right

        val startX = (firstLeft + firstRight) / 2
        val startY = root.textButton7.bottom

        val secondLeft = root.textButton6.left
        val secondRight = root.textButton6.right

        val stopX = (secondLeft + secondRight) / 2
        val stopY = root.textButton6.bottom

        Log.d("COORD", startX.toString())
        Log.d("COORD", startY.toString())
        Log.d("COORD", stopX.toString())
        Log.d("COORD", stopY.toString())

        // root.nodeLayout!!.addView(CanvasView(requireContext()))
        return root
    }
}