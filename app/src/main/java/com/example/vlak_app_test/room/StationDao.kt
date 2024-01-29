package com.example.vlak_app_test.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StationDao {
    @Insert
    suspend fun insert(station: SearchedStation)

    @Query("DELETE FROM searched_stations WHERE id NOT IN (SELECT id FROM searched_stations ORDER BY timestamp DESC LIMIT 5)")
    suspend fun deleteOldSearches()

    @Query("SELECT * FROM searched_stations ORDER BY timestamp DESC LIMIT 5")
    suspend fun getRecentSearches(): List<SearchedStation>
}