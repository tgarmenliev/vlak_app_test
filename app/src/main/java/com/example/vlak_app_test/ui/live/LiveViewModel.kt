package com.example.vlak_app_test.ui.live

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vlak_app_test.models.live.Live
import com.example.vlak_app_test.network.TrainApi
import com.example.vlak_app_test.stationsList
import kotlinx.coroutines.launch
import java.util.Locale

sealed interface LiveState {
    object Loading : LiveState
    data class Success(val data: Live.LiveTable) : LiveState
    data class Error(val error: Throwable) : LiveState
}

class LiveViewModel : ViewModel() {
    var liveState: LiveState by mutableStateOf(LiveState.Loading)

    private val _selectedStation = mutableStateOf("")
    val selectedStation: State<String> = _selectedStation

    private val _selectedType = mutableStateOf("departures")
    val selectedType: State<String> = _selectedType

    //private val _data = MutableLiveData<Live.LiveTable>()
    //private val data: Live.LiveTable = sampleLiveInfo

//    init {
//        data.value = sampleLiveInfo
//    }

    private fun getStationCode(station: String): Int? {
        // find the station id by its name
        val stations = stationsList
        val foundStation = stations.find {
            it.name.equals(station, ignoreCase = true) || it.englishName.equals(station, ignoreCase = true)
        }
        if(foundStation != null) {
            return foundStation.id
        } else {
            return null
        }
    }

    fun checkIfStationExists(station: String): Boolean {
        val stations = stationsList
        val foundStation = stations.find {
            it.name.equals(station, ignoreCase = true) || it.englishName.equals(station, ignoreCase = true)
        }
        return foundStation != null
    }

    fun setStation(station: String) {
        _selectedStation.value = getStationCode()
    }

    fun setType(type: String) {
        _selectedType.value = type
    }

    fun getLiveInfo(): Live.LiveTable {
        return (liveState as LiveState.Success).data
    }

    fun getData() {
        viewModelScope.launch {
            liveState = LiveState.Loading
            liveState = try {
                val result = TrainApi.retrofitService.getLiveInfo(Locale.getDefault().language, selectedStation.value, selectedType.value)
                LiveState.Success(result)
            } catch (e: Exception) {
                LiveState.Error(e)
            }
        }
    }
}