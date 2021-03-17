package com.yuzgulen.laera.ui.exercise

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
    private var startY: Float = 0f
    private var stopX: Float = 0f
    private var stopY: Float = 0f

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CanvasView)
        startX = typedArray.getFloat(R.styleable.CanvasView_startX, 0f)
        startY = typedArray.getFloat(R.styleable.CanvasView_startY, 0f)
        stopX = typedArray.getDimension(R.styleable.CanvasView_stopX, 0f)
        stopY = typedArray.getFloat(R.styleable.CanvasView_stopY, 0f)
        typedArray.recycle()
    }

    override  fun onDraw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.setStrokeWidth(10.toFloat())
        val offset = 0
//        canvas.drawLine(offset.toFloat(), (canvas.height / 2).toFloat(),
//            (canvas.width - offset).toFloat(),
//            (canvas.height / 2).toFloat(), paint)
        canvas.drawLine(startX, startY, stopX, stopY, paint)
    }

    fun setCoordinates(_startX: Float, _startY: Float, _stopX: Float, _stopY: Float) {
        startX = _startX
        startY = _startY
        stopX = _stopX
        stopY = _stopY
        invalidate()
        requestLayout()
    }
}
