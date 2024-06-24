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

    @Query("SELECT * FROM trip_trains WHERE trainNumber = :trainNumber")
    suspend fun getTripTrainsByTrainNumber(trainNumber: String): TripTrains?

    @Query("SELECT COUNT(*) FROM trip_trains WHERE trainNumber = :trainNumber AND date = :date")
    suspend fun checkIfTripTrainsExists(trainNumber: String, date: String): Int

    @Query("SELECT * FROM trip_trains WHERE toBeShown = 1")
    suspend fun getTripTrainsToBeShown(): List<TripTrains>

    @Query("DELETE FROM trip_trains WHERE trainNumber = :trainNumber AND date = :date AND toBeShown = 0")
    suspend fun deleteTripTrainsByTrainNumberAndDate(trainNumber: String, date: String)

    @Query("DELETE FROM trip_trains WHERE trainNumber = :trainNumber AND toBeShown = 1")
    suspend fun deleteTripTrainsByTrainNumber(trainNumber: String)

    @Query("DELETE FROM trip_trains WHERE date = :date")
    suspend fun deleteTripTrainsByDate(date: String)
}