package com.bultrain.vlak_app_test.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searched_schedules")
data class SearchedSchedule(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fromStation: String,
    val toStation: String,
    val fromCode: Int,
    val toCode: Int,
    val timestamp: Long = System.currentTimeMillis()
)
