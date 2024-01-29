package com.example.vlak_app_test.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searched_stations")
data class SearchedStation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val stationName: String,
    val stationCode: Int,
    val timestamp: Long = System.currentTimeMillis()
)
