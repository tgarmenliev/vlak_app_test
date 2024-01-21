package com.example.vlak_app_test.ui.train_info

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vlak_app_test.models.train_info.TrainInfo
import com.example.vlak_app_test.network.TrainApi
import kotlinx.coroutines.launch

sealed interface TrainInfoState {
    object Loading : TrainInfoState
    data class Success(val data: TrainInfo.TrainInfoTable) : TrainInfoState
    data class Error(val error: Throwable) : TrainInfoState
}

class TrainInfoViewModel : ViewModel() {
    var trainInfoState: TrainInfoState by mutableStateOf(TrainInfoState.Loading)

    private val _selectedTrain = mutableStateOf("")
    val selectedTrain: State<String> = _selectedTrain

    fun setTrain(train: String) {
        _selectedTrain.value = train
    }

    fun getTrain(): String {
        return selectedTrain.value
    }

    fun getTrainInfo(): TrainInfo.TrainInfoTable {
        return (trainInfoState as TrainInfoState.Success).data
    }

    fun getData() {
        viewModelScope.launch {
            trainInfoState = TrainInfoState.Loading
            trainInfoState = try {
                val result = TrainApi.retrofitService.getTrainInfo(selectedTrain.value)
                TrainInfoState.Success(result)
            } catch (e: Exception) {
                TrainInfoState.Error(e)
            }
        }
    }
}