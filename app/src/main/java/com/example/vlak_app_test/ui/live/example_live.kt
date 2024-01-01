package com.example.vlak_app_test.ui.live

import com.example.vlak_app_test.viewmodels.live.Live

val sampleLiveInfo = Live.LiveTable(
    station = "Пловдив",
    trains = listOf(
        Live.Trains(
            direction = "София",
            trainNum = "8602",
            trainType = "БВ",
            time = "10:15",
            isDelayed = true,
            delayedTime = "10:00",
            delayInfo = Live.DelayInfo(
                delayMinutes = 15,
                delayString = "15 min",
                delayInfo = "Delayed"
            )
        ),
        Live.Trains(
            direction = "Бургас",
            trainNum = "8601",
            trainType = "БВ",
            time = "16:00",
        ),
        Live.Trains(
            direction = "Септември",
            trainNum = "10114",
            trainType = "ПВ",
            time = "19:00",
        )
    )
)