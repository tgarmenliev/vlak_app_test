package com.example.vlak_app_test.room

import androidx.room.PrimaryKey

data class Trip(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

)
