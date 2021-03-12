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
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery.view.*
import de.blox.graphview.tree.BuchheimWalkerAlgorithm
import de.blox.graphview.tree.BuchheimWalkerConfiguration
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import de.blox.graphview.GraphView
import android.R
import androidx.annotation.NonNull
import de.blox.graphview.GraphAdapter
import de.blox.graphview.Graph
import de.blox.graphview.Node


class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(com.yuzgulen.laera.R.layout.fragment_gallery, container, false)

        return root
    }
}