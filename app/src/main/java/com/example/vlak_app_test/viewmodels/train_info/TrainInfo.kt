package com.example.vlak_app_test.viewmodels.train_info

class TrainInfo {
    data class TrainInfoTable(
        val trainType: String,
        val trainNum: String,
        val stations: List<StationOnTrainInfo>
    )

    data class StationOnTrainInfo(
        val station: String,
        val arrive: String,
        val depart: String
    )
}