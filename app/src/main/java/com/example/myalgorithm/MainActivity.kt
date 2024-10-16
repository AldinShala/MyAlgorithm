package com.example.myalgorithm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bubbleSortButton: Button = findViewById(R.id.buttonBubbleSort)
        val quickSortButton: Button = findViewById(R.id.buttonQuickSort)
        val mergeSortButton: Button = findViewById(R.id.buttonMergeSort)

        bubbleSortButton.setOnClickListener {
            val intent = Intent(this, BubbleSortActivity::class.java)
            startActivity(intent)
        }

        quickSortButton.setOnClickListener {
            val intent = Intent(this, QuickSortActivity::class.java)
            startActivity(intent)
        }

        mergeSortButton.setOnClickListener {
            val intent = Intent(this, MergeSortActivity::class.java)
            startActivity(intent)

        }
    }
}