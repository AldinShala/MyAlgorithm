package com.example.myalgorithm

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SortingVisualizerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var array = intArrayOf() // Holds the values for the bars
    private val paint = Paint()

    private var currentBar: Int = -1 // Index of the currently active bar
    private var comparisonBar: Int = -1 // Index of the bar being compared

    fun setArray(array: IntArray) {
        this.array = array
        invalidate() // Redraw the view when the array is updated
    }

    fun setComparisonBars(currentBar: Int, comparisonBar: Int) {
        this.currentBar = currentBar
        this.comparisonBar = comparisonBar
        invalidate() // Redraw the view to show the comparison bars
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (array.isEmpty()) return // Early return if the array is empty

        // Define bar properties
        val totalWidth = width.toFloat()
        val totalHeight = height.toFloat()
        val barWidth = totalWidth / array.size.toFloat() * 0.7f
        val spaceBetweenBars = totalWidth / array.size.toFloat() * 0.2f
        val cornerRadius = 80f // Corner radius for rounded edges

        // Calculate gaps
        val leftGap = (totalWidth * 0.15f)
        val rightGap = (totalWidth * 0.15f)
        val bottomGap = (totalHeight * 0.15f)

        // Calculate available width for bars
        val availableWidth = totalWidth - leftGap - rightGap
        val startX = leftGap + (availableWidth - (barWidth * array.size + spaceBetweenBars * (array.size - 1))) / 2

        val textPaint = Paint().apply {
            color = Color.WHITE
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }

        // Calculate and draw each bar
        for (i in array.indices) {
            // Set the color based on the current state
            paint.color = when {
                i == currentBar -> Color.GRAY
                i == comparisonBar -> Color.BLACK
                else -> Color.LTGRAY
            }

            val left = startX + i * (barWidth + spaceBetweenBars) // Starting position of the bar
            val top = totalHeight - bottomGap - (array[i].toFloat() / 100 * (totalHeight - bottomGap))
            val right = left + barWidth
            val bottom = totalHeight - bottomGap // Keep bottom aligned considering the gap

            canvas.drawRoundRect(left, top, right, bottom, cornerRadius, cornerRadius, paint)

            // Numbers below the bars
            val numberX = left + barWidth / 2
            val numberY = bottom + 40 // Position the number below the bar (adjust as needed)
            canvas.drawText(array[i].toString(), numberX, numberY, textPaint)
        }
    }
}
