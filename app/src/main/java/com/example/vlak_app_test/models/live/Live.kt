package com.example.vlak_app_test.models.live

class Live {
    data class LiveTable(
        val station: String,
        val trains: List<Trains>
    )

    data class Trains(
        val direction: String,
        val trainNum: String,
        val trainType: String,
        val time: String,
        val isDelayed: Boolean = false,
        val delayedTime: String = "",
        val delayInfo: DelayInfo = DelayInfo(0, "", "")
    )

    data class DelayInfo(
        val delayMinutes: Int,
        val delayString: String,
        val delayInfo: String
    )
}