package com.bultrain.vlak_app_test.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trip: Trip)

    @Query("""
        DELETE FROM trip 
        WHERE date(substr(departureDate, 7, 4) || '-' || substr(departureDate, 4, 2) || '-' || substr(departureDate, 1, 2)) < date(:currentDate)
    """)
    suspend fun deleteOldTrips(currentDate: String)

    @Query("SELECT * FROM trip ORDER BY timestamp ASC")
    suspend fun getTrips(): List<Trip>

    @Query("SELECT id, route, duration, departureTime, arrivalTime, departureDate, arrivalDate FROM trip ORDER BY timestamp ASC LIMIT 3")
    suspend fun getRecent3TripTitles(): List<TripHeading>

    @Query("DELETE FROM trip WHERE id = :id")
    suspend fun deleteTripById(id: Int)

    @Query("SELECT COUNT(*) FROM trip WHERE route = :route AND departureDate = :departureDate AND departureTime = :departureTime")
    suspend fun checkIfTripExists(route: String, departureDate: String, departureTime: String): Int
}