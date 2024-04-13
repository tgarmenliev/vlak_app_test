package com.bultrain.vlak_app_test.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bultrain.vlak_app_test.models.train_info.TrainInfo

@Entity(tableName = "trip_trains")
data class TripTrains(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val trainType: String,
    val trainNumber: String,
    val date: String,
    val stations: List<TrainInfo.StationOnTrainInfo> = emptyList()
)
