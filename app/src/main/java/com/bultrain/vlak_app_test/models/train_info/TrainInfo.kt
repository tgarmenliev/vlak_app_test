package com.bultrain.vlak_app_test.models.train_info

class TrainInfo {
    data class TrainInfoTable(
        val trainType: String = "",
        val trainNumber: String = "",
        val date: String = "",
        val stations: List<StationOnTrainInfo> = emptyList()
    )

    data class StationOnTrainInfo(
        val station: String = "",
        val arrive: String = "",
        val depart: String = ""
    )
}