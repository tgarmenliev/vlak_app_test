package com.example.vlak_app_test.ui.live

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LiveViewModel : ViewModel() {
    private val _selectedStation = mutableStateOf("")
    val selectedStation: State<String> = _selectedStation

    //private val _data = MutableLiveData<Live.LiveTable>()
    private val data: Live.LiveTable = sampleLiveInfo

//    init {
//        data.value = sampleLiveInfo
//    }

    fun setStation(station: String) {
        _selectedStation.value = station
    }

    fun getStation(): String {
        return selectedStation.value
    }

    fun getData(): Live.LiveTable {
        return data
    }
}