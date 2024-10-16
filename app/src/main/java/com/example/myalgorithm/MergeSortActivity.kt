package com.example.myalgorithm

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class MergeSortActivity : AppCompatActivity() {

    private lateinit var visualizerView: SortingVisualizerView
    private lateinit var backButton: Button // Declare the back button
    private var array = IntArray(10) // Adjust size as needed
    private val delayMillis: Long = 250 // Delay in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merge_sort)

        visualizerView = findViewById(R.id.visualizerView)
        backButton = findViewById(R.id.backButton) // Initialize the back button
        backButton.setBackgroundColor(Color.parseColor("#CCCCCC")) // Change to desired color
        backButton.setTextColor(Color.BLACK) // Optionally change text color
        window.decorView.setBackgroundColor(Color.parseColor("#2B2D30"))

        // Generate random array
        array = IntArray(array.size) { (0..100).random() }
        visualizerView.setArray(array)

        backButton.setOnClickListener {
            Log.d("MergeSortActivity", "Back button clicked")
            finish() // Close the current activity and return to the previous one
        }

        mergeSort(array)
    }

    private fun mergeSort(arr: IntArray) {
        GlobalScope.launch {
            mergeSortHelper(arr, 0, arr.size - 1)
            // Reset comparison indicators after sorting
            visualizerView.setComparisonBars(-1, -1)
        }
    }

    private suspend fun mergeSortHelper(arr: IntArray, left: Int, right: Int) {
        if (left < right) {
            val mid = (left + right) / 2
            // Sort first half
            mergeSortHelper(arr, left, mid)
            // Sort second half
            mergeSortHelper(arr, mid + 1, right)
            // Merge the sorted halves
            merge(arr, left, mid, right)
        }
    }

    private suspend fun merge(arr: IntArray, left: Int, mid: Int, right: Int) {
        // Create temporary arrays for left and right
        val leftArray = arr.sliceArray(left..mid)
        val rightArray = arr.sliceArray(mid + 1..right)

        var i = 0 // Initial index of first
        var j = 0 // Initial index of second
        var k = left // Initial index of merged

        while (i < leftArray.size && j < rightArray.size) {
            // Track the bars being compared
            visualizerView.setComparisonBars(k, if (j < rightArray.size) (k + 1) else k)

            // Compare and merge
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i]
                i++
            } else {
                arr[k] = rightArray[j]
                j++
            }


            runOnUiThread {
                visualizerView.setArray(arr)
            }

            k++

            delay(delayMillis)
        }


        while (i < leftArray.size) {
            arr[k] = leftArray[i]
            i++
            k++

            runOnUiThread {
                visualizerView.setArray(arr)
            }
            delay(delayMillis)
        }


        while (j < rightArray.size) {
            arr[k] = rightArray[j]
            j++
            k++

            runOnUiThread {
                visualizerView.setArray(arr)
            }
            delay(delayMillis)
        }
    }
}