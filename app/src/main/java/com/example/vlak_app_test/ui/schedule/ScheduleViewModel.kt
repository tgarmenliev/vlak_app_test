package com.example.vlak_app_test.ui.schedule

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.vlak_app_test.viewmodels.schedule.Schedule
import com.example.vlak_app_test.viewmodels.schedule.sampleScheduleInfo

class ScheduleViewModel : ViewModel() {
    private val _selectedStations = mutableStateOf(ScheduleOption("", "", ""))
    val selectedStations: State<ScheduleOption> = _selectedStations

    private val data: Schedule.ScheduleTable = sampleScheduleInfo

    fun setOption(from: String, to: String, date: String) {
        _selectedStations.value = ScheduleOption(from, to, date)
    }

    fun getData(): Schedule.ScheduleTable {
        return data
    }
}

data class ScheduleOption(
    val from: String,
    val to: String,
    val date: String,
)
