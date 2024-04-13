package com.bultrain.vlak_app_test.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TripTrainsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tripTrains: TripTrains)

    @Query("SELECT * FROM trip_trains WHERE trainNumber = :trainNumber AND date = :date")
    suspend fun getTripTrainsByTrainNumberAndDate(trainNumber: String, date: String): TripTrains?

    @Query("SELECT COUNT(*) FROM trip_trains WHERE trainNumber = :trainNumber AND date = :date")
    suspend fun checkIfTripTrainsExists(trainNumber: String, date: String): Int

    @Query("DELETE FROM trip_trains WHERE trainNumber = :trainNumber AND date = :date")
    suspend fun deleteTripTrainsByTrainNumberAndDate(trainNumber: String, date: String)

    @Query("DELETE FROM trip_trains WHERE date = :date")
    suspend fun deleteTripTrainsByDate(date: String)
}