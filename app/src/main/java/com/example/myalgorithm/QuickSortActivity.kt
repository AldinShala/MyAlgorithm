package com.example.myalgorithm

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class QuickSortActivity : AppCompatActivity() {

    private lateinit var visualizerView: SortingVisualizerView
    private lateinit var backButton: Button
    private var array = IntArray(20)
    private val delayMillis: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_sort)

        visualizerView = findViewById(R.id.visualizerView)
        backButton = findViewById(R.id.backButton)
        backButton.setBackgroundColor(Color.parseColor("#CCCCCC"))
        backButton.setTextColor(Color.BLACK)
        window.decorView.setBackgroundColor(Color.parseColor("#2B2D30"))

        // Generate random array
        array = IntArray(array.size) { (0..100).random() }
        visualizerView.setArray(array)

        backButton.setOnClickListener {
            Log.d("QuickSortActivity", "Back button clicked")
            finish() // Close the current activity and return to the previous one
        }

        quickSort(array, 0, array.size - 1)
    }

    private fun quickSort(arr: IntArray, low: Int, high: Int) {
        if (low < high) {
            val pi = partition(arr, low, high)

            // Update the visualizer after partitioning
            runOnUiThread {
                visualizerView.setArray(arr)
            }

            // Launch a coroutine for sorting the next parts
            GlobalScope.launch {
                delay(delayMillis) // Delay after updating the visualizer

                quickSort(arr, low, pi - 1)
                quickSort(arr, pi + 1, high)
            }
        }
    }

    private fun partition(arr: IntArray, low: Int, high: Int): Int {
        val pivot = arr[high]
        var i = low - 1

        for (j in low until high) {
            // Track which bars are being compared
            visualizerView.setComparisonBars(i, j)

            if (arr[j] < pivot) {
                i++
                // Swap arr[i] and arr[j]
                val temp = arr[i]
                arr[i] = arr[j]
                arr[j] = temp
            }

            // Update the visualizer on the UI thread
            runOnUiThread {
                visualizerView.setArray(arr)
            }


        }

        val temp = arr[i + 1]
        arr[i + 1] = arr[high]
        arr[high] = temp

        return i + 1
        }
    }
