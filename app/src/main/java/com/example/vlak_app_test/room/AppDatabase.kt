package com.example.vlak_app_test.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SearchedStation::class, SearchedSchedule::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao

    abstract fun scheduleDao(): ScheduleDao
}