package com.bultrain.vlak_app_test.ui.live

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bultrain.vlak_app_test.models.live.Live
import com.bultrain.vlak_app_test.network.TrainApi
import com.bultrain.vlak_app_test.room.SearchedStation
import com.bultrain.vlak_app_test.room.StationDao
import com.bultrain.vlak_app_test.stationsList
import kotlinx.coroutines.launch
import java.util.Locale

sealed interface LiveState {
    data object Loading : LiveState
    data class Success(val data: Live.LiveTable) : LiveState
    data class Error(val error: Throwable) : LiveState
}

class LiveViewModel(
    private val dao: StationDao
) : ViewModel() {
    var liveState: LiveState by mutableStateOf(LiveState.Loading)

    private val _selectedStation = mutableIntStateOf(0)
    private val selectedStation: State<Int> = _selectedStation

    private val _selectedStationName = mutableStateOf("")

    private val _selectedType = mutableStateOf("departures")
    private val selectedType: State<String> = _selectedType

    private val _recentSearches = mutableStateOf<List<SearchedStation>>(emptyList())
    val recentSearches: State<List<SearchedStation>> = _recentSearches

    private fun getStationCode(station: String): Int {
        val stations = stationsList
        val foundStation = stations.find {
            it.name.equals(station, ignoreCase = true) || it.englishName.equals(station, ignoreCase = true)
        }
        if(foundStation != null) {
            return foundStation.id
        }
        return 0
    }

    fun checkIfStationExists(station: String): Boolean {
        val stations = stationsList
        val foundStation = stations.find {
            it.name.equals(station, ignoreCase = true) || it.englishName.equals(station, ignoreCase = true)
        }
        return foundStation != null
    }

    fun setStation(station: String) {
        _selectedStationName.value = station
        _selectedStation.intValue = getStationCode(station)
    }

    fun setType(type: String) {
        _selectedType.value = type
    }

    fun getType(): String {
        return selectedType.value
    }

    fun getLiveInfo(): Live.LiveTable {
        return (liveState as LiveState.Success).data
    }

    suspend fun getRecentSearches() {
        _recentSearches.value = dao.getRecentSearches()
    }

    fun getData() {
        viewModelScope.launch {
            liveState = LiveState.Loading

            // If the station is not in the database for recent searches, add it
            if (dao.getStationCount(selectedStation.value) == 0) {
                dao.deleteOldSearches()
                SearchedStation(stationName = _selectedStationName.value, stationCode = selectedStation.value).also {
                    dao.insert(it)
                }
            }

            // Get the live info from the API
            liveState = try {
                val result = TrainApi.retrofitService.getLiveInfo(Locale.getDefault().language, selectedStation.value, selectedType.value)
                LiveState.Success(result)
            } catch (e: Exception) {
                LiveState.Error(e)
            }
        }
    }
}