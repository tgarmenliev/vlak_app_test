package com.bultrain.vlak_app_test.room

import androidx.room.TypeConverter
import com.bultrain.vlak_app_test.models.schedule.Schedule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromTrainsList(value: List<Schedule.Trains>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toTrainsList(value: String): List<Schedule.Trains> {
        val gson = Gson()
        val type = object : TypeToken<List<Schedule.Trains>>() {}.type
        return gson.fromJson(value, type)
    }
}