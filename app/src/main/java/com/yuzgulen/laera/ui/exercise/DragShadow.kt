package com.yuzgulen.laera.ui.exercise

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.yuzgulen.laera.R
import java.lang.StringBuilder

class DragShadow(v: View, key: CharSequence) : View.DragShadowBuilder(v) {

    private val shadow = DraggedKey(key, view.width, view.height)

    override fun onProvideShadowMetrics(size: Point, touch: Point) {
        val width: Int = view.width
        val height: Int = view.height
        size.set(width, height)
        touch.set(width / 2, height / 2)
    }

    override fun onDrawShadow(canvas: Canvas) {
        shadow.draw(canvas)
    }
}