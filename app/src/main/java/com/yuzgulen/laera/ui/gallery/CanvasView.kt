package com.yuzgulen.laera.ui.gallery

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.yuzgulen.laera.R


class CanvasView(context: Context, attributeSet: AttributeSet)
    : View(context, attributeSet) {

    private var startX: Float = 0f
    private var startY: Int = 0
    private var stopX: Float = 0f
    private var stopY: Int = 0

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CanvasView)
        startX = typedArray.getDimension(R.styleable.CanvasView_startX, 0f)
        startY = typedArray.getColor(R.styleable.CanvasView_startY, 0)
        stopX = typedArray.getDimension(R.styleable.CanvasView_stopX, 0f)
        stopY = typedArray.getColor(R.styleable.CanvasView_stopY, 0)
        typedArray.recycle()
    }

    override  fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.RED)
        val paint = Paint()
        paint.color = Color.YELLOW
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8F
        paint.isAntiAlias = true
        val offset = 50
//        val firstLeft = textButton7.left
//        val firstRight = textButton7.right
//
//        val startX = (firstLeft + firstRight).toFloat() / 2
//        val startY = textButton7.bottom.toFloat()
//
//        val secondLeft = textButton6.left
//        val secondRight = textButton6.right
//
//        val stopX = (secondLeft + secondRight).toFloat() / 2
//        val stopY = textButton6.bottom.toFloat()
//        canvas.drawLine(offset.toFloat(), (canvas.height / 2).toFloat(),
//            (canvas.width - offset).toFloat(),
//            (canvas.height / 2).toFloat(), paint)
        //canvas.drawLine(startX, startY.toFloat(), stopX, stopY.toFloat(), paint)
    }
}