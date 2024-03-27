package com.bultrain.vlak_app_test.ui.schedule

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bultrain.vlak_app_test.models.schedule.Schedule
import com.bultrain.vlak_app_test.network.TrainApi
import com.bultrain.vlak_app_test.room.ScheduleDao
import com.bultrain.vlak_app_test.room.SearchedSchedule
import com.bultrain.vlak_app_test.stationsList
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

sealed interface ScheduleState {
    data object Loading : ScheduleState
    data class Success(val data: Schedule.ScheduleTable) : ScheduleState
    data class Error(val error: Throwable) : ScheduleState
}

class ScheduleViewModel(
    private val dao: ScheduleDao
) : ViewModel() {
    var scheduleState: ScheduleState by mutableStateOf(ScheduleState.Loading)

    private val _selectedStations = mutableStateOf(ScheduleOption(0, "", 0, "", ""))
    private val selectedStations: State<ScheduleOption> = _selectedStations

    private val _ifToday = mutableStateOf(true)

    private val _selectedOption = mutableIntStateOf(0)
    private val selectedOption: State<Int> = _selectedOption

    private val _recentSearches = mutableStateOf<List<SearchedSchedule>>(emptyList())
    val recentSearches: State<List<SearchedSchedule>> = _recentSearches

    private fun getStationCode(station: String): Int? {
        val stations = stationsList
        return stations.find {
            it.name.equals(station, ignoreCase = true) || it.englishName.equals(
                station,
                ignoreCase = true
            )
        }?.id
    }

    fun checkIfStationExists(station: String): Boolean {
        if (station.isEmpty()) return false
        val stations = stationsList
        val foundStation = stations.find {
            it.name.equals(station, ignoreCase = true) || it.englishName.equals(station, ignoreCase = true)
        }
        return foundStation != null
    }

    fun setOption(from: String, to: String, date: String) {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val todayString = formatter.format(Date(Calendar.getInstance().timeInMillis))
        _ifToday.value = todayString == date

        _selectedStations.value = ScheduleOption(getStationCode(from)!!, from, getStationCode(to)!!, to, date)
    }

    fun setOptionIndex(index: Int) {
        _selectedOption.intValue = index
    }

    fun getDataOption(): Schedule.Options {
        return (scheduleState as ScheduleState.Success).data.options[selectedOption.value]
    }

    fun getRoute(): String {
        return (scheduleState as ScheduleState.Success).data.route
    }

    fun getScheduleInfo(): Schedule.ScheduleTable {
        return (scheduleState as ScheduleState.Success).data
    }

    suspend fun getRecentSearches() {
        _recentSearches.value = dao.getRecentSearches()
    }

    fun getData() {
        viewModelScope.launch {
            scheduleState = ScheduleState.Loading

            if (
                dao.getScheduleCount(
                    selectedStations.value.from,
                    selectedStations.value.to
                ) == 0
            ) {
                dao.deleteOldSearches()
                SearchedSchedule(
                    fromStation = selectedStations.value.fromName,
                    fromCode = selectedStations.value.from,
                    toStation = selectedStations.value.toName,
                    toCode = selectedStations.value.to
                ).also {
                    dao.insert(it)
                }
            }

            scheduleState = try {
                val result = (_ifToday.value).let {
                    if (it) {
                        TrainApi.retrofitService.getScheduleInfoToday(
                            Locale.getDefault().language,
                            selectedStations.value.from,
                            selectedStations.value.to
                        )
                    } else {
                        TrainApi.retrofitService.getScheduleInfo(
                            Locale.getDefault().language,
                            selectedStations.value.from,
                            selectedStations.value.to,
                            selectedStations.value.date
                        )
                    }
                }
                ScheduleState.Success(result)
            } catch (e: Exception) {
                ScheduleState.Error(e)
            }
        }
    }
}

data class ScheduleOption(
    val from: Int,
    val fromName: String,
    val to: Int,
    val toName: String,
    val date: String,
)
