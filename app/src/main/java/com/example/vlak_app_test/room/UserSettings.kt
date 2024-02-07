package com.example.vlak_app_test.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val theme: String = "auto",
    val language: String = "auto",
    val name: String = "",
)
