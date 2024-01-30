package com.example.vlak_app_test.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SearchedStation::class, SearchedSchedule::class, SearchedTrainInfo::class, Trip::class],
    version = 3,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao

    abstract fun scheduleDao(): ScheduleDao

    abstract fun trainInfoDao(): TrainInfoDao

    abstract fun tripDao(): TripDao
}