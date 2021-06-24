package com.yuzgulen.laera.ui.exercise

import android.R.attr.bottom
import android.R.attr.right
import android.R.attr.top
import android.R.attr.left
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.databinding.adapters.TextViewBindingAdapter.setTextSize




class DraggedKey(key: CharSequence, w: Int, h: Int) : Drawable() {
    private val mPaint: Paint
    private val mRect: RectF
    private val keyValue: CharSequence = key
    private val width: Int = w
    private val height: Int = h

    init {
        mPaint = Paint()
        mRect = RectF()
    }

    override fun draw(canvas: Canvas) {
        // Set the correct values in the Paint
        mPaint.setARGB(255, 255, 0, 0)
        mPaint.setStrokeWidth(2F)
        mPaint.setStyle(Paint.Style.FILL)

        // Adjust the rectangle
        mRect.left = 15.0f
        mRect.top = 50.0f
        mRect.right = 55.0f
        mRect.bottom = 75.0f

        // Draw it
        canvas.drawRoundRect(mRect, 0.5f, 0.5f, mPaint)
        val paint = Paint()
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        canvas.drawPaint(paint)

        paint.color = Color.BLACK
        paint.textSize = 60F
        canvas.drawText(keyValue.toString(), width/2F, height/2F, paint)
    }

    override fun setAlpha(p0: Int) {
        mPaint.alpha = p0
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setColorFilter(arg0: ColorFilter?) {
        if (arg0 != null) {
            mPaint.colorFilter = arg0
        }
    }
}