package com.example.vlak_app_test.models.schedule

class Schedule {
    data class ScheduleTable(
        val date: String = "",
        val route: String = "",
        val totalTrains: Int = 0,
        val options: List<Options>
    )

    data class Options(
        val duration: String = "",
        val departureTime: String = "",
        val arrivalTime: String = "",
        val departureDate: String = "",
        val arrivalDate: String = "",
        val numOfTransfers: Int = 0,
        val trains: List<Trains>
    )

    data class Trains(
        val from: String = "",
        val to: String = "",
        val depart: String = "",
        val arrive: String = "",
        val departDate: String = "",
        val arriveDate: String = "",
        val trainType: String = "",
        val trainNumber: String = "",
        val duration: String = "",
        val timeToWaitNext: String = ""
    )
}