package com.example.vlak_app_test.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// This annotation tells Room to create a database with the name "app_database" and the version 8
@Database(
    entities = [SearchedStation::class, SearchedSchedule::class, SearchedTrainInfo::class, Trip::class],
    version = 8,
    exportSchema = false
)
@TypeConverters(Converters::class) // This annotation tells Room to use the Converters class to convert the data types
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao

    abstract fun scheduleDao(): ScheduleDao

    abstract fun trainInfoDao(): TrainInfoDao

    abstract fun tripDao(): TripDao
}