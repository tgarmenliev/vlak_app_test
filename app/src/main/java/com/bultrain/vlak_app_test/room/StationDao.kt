package com.bultrain.vlak_app_test.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(station: SearchedStation)

    @Query("DELETE FROM searched_stations WHERE id NOT IN (SELECT id FROM searched_stations ORDER BY timestamp DESC LIMIT 3)")
    suspend fun deleteOldSearches()

    @Query("SELECT * FROM searched_stations ORDER BY timestamp DESC LIMIT 3")
    suspend fun getRecentSearches(): List<SearchedStation>

    @Query("SELECT COUNT(*) FROM searched_stations WHERE stationCode = :code")
    suspend fun getStationCount(code: Int): Int
}