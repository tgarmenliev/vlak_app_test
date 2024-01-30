package com.example.vlak_app_test.room

import androidx.room.PrimaryKey

data class SearchedTrainInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val trainNumber: String,
    val timestamp: Long = System.currentTimeMillis()
)