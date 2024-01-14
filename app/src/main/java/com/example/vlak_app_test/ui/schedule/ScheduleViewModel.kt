package com.example.vlak_app_test.ui.schedule

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.vlak_app_test.models.schedule.Schedule

class ScheduleViewModel : ViewModel() {
    private val _selectedStations = mutableStateOf(ScheduleOption("", "", ""))
    private val selectedStations: State<ScheduleOption> = _selectedStations

    private val _selectedOption = mutableIntStateOf(0)
    private val selectedOption: State<Int> = _selectedOption

    private val data: Schedule.ScheduleTable = sampleScheduleInfo

    fun setOption(from: String, to: String, date: String) {
        _selectedStations.value = ScheduleOption(from, to, date)
    }

    fun setOptionIndex(index: Int) {
        _selectedOption.value = index
    }

    fun getOptionIndex(): Int {
        return selectedOption.value
    }

    fun getDataOption(): Schedule.Options {
        return data.options[selectedOption.value]
    }

    fun getRoute(): String {
        return data.route
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
