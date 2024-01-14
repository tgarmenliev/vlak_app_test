package com.example.vlak_app_test.ui.live

import com.example.vlak_app_test.R
import com.example.vlak_app_test.models.guide.Guide
import com.example.vlak_app_test.models.live.Live

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

val sampleGuideInfo = listOf(
    Guide.GuideTable(
        title = "Пътуване с влак1",
        shortDescription = "Пътуване с влак почти дълго1",
        description = "Пътуване с влак дълго1",
        image = R.drawable.train_one
    ),
    Guide.GuideTable(
        title = "Пътуване с влак2",
        shortDescription = "Пътуване с влак почти2",
        description = "Пътуване с влак много дълго2",
        image = R.drawable.train_two
    ),
    Guide.GuideTable(
        title = "Пътуване с 30123",
        shortDescription = "Пътуване с 30123 уникално",
        description = "Избери пътуването с 30123 още сега!",
        image = R.drawable.train_three
    ),
)