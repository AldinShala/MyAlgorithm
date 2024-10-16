package com.example.myalgorithm

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay


class BubbleSortActivity : AppCompatActivity() {

    private lateinit var visualizerView: SortingVisualizerView
    private lateinit var backButton: Button // Declare the back button
    private var array = IntArray(10) // Adjust size as needed
    private val delayMillis: Long = 250 // Delay in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble_sort)

        visualizerView = findViewById(R.id.visualizerView)
        backButton = findViewById(R.id.backButton)
        backButton.setBackgroundColor(Color.parseColor("#CCCCCC"))
        backButton.setTextColor(Color.BLACK) // Optionally change text color
        window.decorView.setBackgroundColor(Color.parseColor("#2B2D30"))
        
        // Generate random array
        array = IntArray(array.size) { (0..100).random() }
        visualizerView.setArray(array)

        // Set up the back button click listener
        backButton.setOnClickListener {
            Log.d("BubbleSortActivity", "Back button clicked")
            finish()       // Close the current activity and return to the previous one
        }

        bubbleSort()
    }

    private fun bubbleSort() {
        GlobalScope.launch {
            for (i in array.indices) {
                for (j in 0 until array.size - i - 1) {
                    // Track the bars being compared
                    visualizerView.setComparisonBars(j, j + 1)

                    if (array[j] > array[j + 1]) {
                        // Swap
                        val temp = array[j]
                        array[j] = array[j + 1]
                        array[j + 1] = temp

                        // Update the visualizer on the UI thread
                        runOnUiThread {
                            visualizerView.setArray(array)
                        }
                    }

                    // Delay to visualize the sorting
                    delay(delayMillis)
                }
            }

            // Reset comparison indicators after sorting
            visualizerView.setComparisonBars(-1, -1)
        }
    }
}