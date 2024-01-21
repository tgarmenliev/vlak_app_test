package com.example.vlak_app_test.ui.live

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vlak_app_test.models.live.Live
import com.example.vlak_app_test.network.TrainApi
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

    fun setStation(station: String) {
        _selectedStation.value = station
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