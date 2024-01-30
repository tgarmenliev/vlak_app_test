package com.example.vlak_app_test.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searched_train_info")
data class SearchedTrainInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val trainNumber: String,
    val timestamp: Long = System.currentTimeMillis()
)
