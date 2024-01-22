package com.example.vlak_app_test.network

import com.example.vlak_app_test.models.live.Live
import com.example.vlak_app_test.models.schedule.Schedule
import com.example.vlak_app_test.models.train_info.TrainInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://192.168.1.5:3000"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface TrainApiService {

    @GET("/api/train-info/{language}/{num}")
    suspend fun getTrainInfo(
        @Path("language") language: String,
        @Path("num") num: String
    ): TrainInfo.TrainInfoTable

    @GET("/api/train-info/{language}/{num}/{date}")
    suspend fun getTrainInfo(
        @Path("language") language: String,
        @Path("num") num: String,
        @Path("date") date: String? = null
    ): TrainInfo.TrainInfoTable

    @GET("/api/live/{language}/{stationNumber}/{type}")
    suspend fun getLiveInfo(
        @Path("language") language: String,
        @Path("stationNumber") stationNumber: Int,
        @Path("type") type: String
    ): Live.LiveTable

    @GET("/api/schedule/{language}/{from}/{to}")
    suspend fun getScheduleInfoToday(
        @Path("language") language: String,
        @Path("from") from: Int,
        @Path("to") to: Int
    ): Schedule.ScheduleTable

    @GET("/api/schedule/{language}/{from}/{to}/{date}")
    suspend fun getScheduleInfo(
        @Path("language") language: String,
        @Path("from") from: Int,
        @Path("to") to: Int,
        @Path("date") date: String
    ): Schedule.ScheduleTable
}

object TrainApi {
    val retrofitService: TrainApiService by lazy {
        retrofit.create(TrainApiService::class.java)
    }
}