package com.example.vlak_app_test.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vlak_app_test.models.schedule.Schedule

@Entity(tableName = "trip")
data class Trip(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val fromStation: String,
    val toStation: String,
    val duration: String,
    val departureTime: String,
    val arrivalTime: String,
    val departureDate: String,
    val arrivalDate: String,
    val numOfTransfers: Int,
    val trains: List<Schedule.Trains>,
    val timestamp: Long = System.currentTimeMillis()
)

data class TripHeading(
    val fromStation: String,
    val toStation: String,
    val duration: String,
    val departureTime: String,
    val arrivalTime: String,
)