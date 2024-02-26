package com.example.vlak_app_test.room

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var instance: AppDatabase? = null

    // Function to build the database
    fun buildDatabase(context: Context): AppDatabase {
        return instance ?: synchronized(this) {
            instance ?: createDatabase(context).also { instance = it }
        }
    }

    // Function to create the database
    private fun createDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}