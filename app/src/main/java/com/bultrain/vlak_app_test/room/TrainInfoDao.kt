package com.bultrain.vlak_app_test.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrainInfoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trainInfo: SearchedTrainInfo)

    @Query("DELETE FROM searched_train_info WHERE id NOT IN (SELECT id FROM searched_train_info ORDER BY timestamp DESC LIMIT 3)")
    suspend fun deleteOldSearches()

    @Query("SELECT * FROM searched_train_info ORDER BY timestamp DESC LIMIT 3")
    suspend fun getRecentSearches(): List<SearchedTrainInfo>

    @Query("SELECT COUNT(*) FROM searched_train_info WHERE trainNumber = :trainNumber")
    suspend fun getTrainInfoCount(trainNumber: String): Int
}