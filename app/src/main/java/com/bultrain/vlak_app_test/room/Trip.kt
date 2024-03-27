package com.bultrain.vlak_app_test.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bultrain.vlak_app_test.models.schedule.Schedule

@Entity(tableName = "trip")
data class Trip(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val route: String,
    val duration: String,
    val departureTime: String,
    val arrivalTime: String,
    val departureDate: String,
    val arrivalDate: String,
    val numOfTransfers: Int,
    val trains: List<Schedule.Trains> = emptyList(),
    val timestamp: Long = System.currentTimeMillis()
)

data class TripHeading(
    val id: Int,
    val route: String,
    val duration: String,
    val departureTime: String,
    val arrivalTime: String,
    val departureDate: String,
    val arrivalDate: String,
)