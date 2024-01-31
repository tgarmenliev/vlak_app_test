package com.example.vlak_app_test.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trip: Trip)

    @Query("DELETE FROM trip WHERE timestamp < :timestamp")
    suspend fun deleteOldTrips(timestamp: Long)

    @Query("SELECT id, route, duration, departureTime, arrivalTime FROM trip ORDER BY timestamp DESC")
    suspend fun getTripsTitles(): List<TripHeading>

    @Query("SELECT * FROM trip ORDER BY timestamp DESC")
    suspend fun getTrips(): List<Trip>

    @Query("SELECT * FROM trip WHERE id = :id")
    suspend fun getTrip(id: Int): Trip

    @Query("SELECT id, route, duration, departureTime, arrivalTime FROM trip ORDER BY timestamp DESC LIMIT 3")
    suspend fun getRecent3TripTitles(): List<TripHeading>
}