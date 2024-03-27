package com.bultrain.vlak_app_test.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(schedule: SearchedSchedule)

    @Query("DELETE FROM searched_schedules WHERE id NOT IN (SELECT id FROM searched_schedules ORDER BY timestamp DESC LIMIT 3)")
    suspend fun deleteOldSearches()

    @Query("SELECT * FROM searched_schedules ORDER BY timestamp DESC LIMIT 3")
    suspend fun getRecentSearches(): List<SearchedSchedule>

    @Query("SELECT COUNT(*) FROM searched_schedules WHERE fromCode = :fromCode AND toCode = :toCode")
    suspend fun getScheduleCount(fromCode: Int, toCode: Int): Int
}