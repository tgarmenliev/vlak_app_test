package com.bultrain.vlak_app_test.network

import com.bultrain.vlak_app_test.models.guide.Guide
import com.bultrain.vlak_app_test.models.live.Live
import com.bultrain.vlak_app_test.models.schedule.Schedule
import com.bultrain.vlak_app_test.models.train_info.TrainInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// Base URL for the API requests
const val BASE_URL = "https://bultrain-backend-9e5d2178614e.herokuapp.com"

// Retrofit object for the requests
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface TrainApiService {

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

    @GET("/api/guide/{language}/{topic}")
    suspend fun getGuideTopic(
        @Path("language") language: String,
        @Path("topic") topic: Int
    ): Guide.Topic

    @GET("/api/guide/{language}")
    suspend fun getGuideTopics(
        @Path("language") language: String
    ): List<Guide.AllTopics>
}

// Singleton object to use the Retrofit functions
object TrainApi {
    val retrofitService: TrainApiService by lazy {
        retrofit.create(TrainApiService::class.java)
    }
}