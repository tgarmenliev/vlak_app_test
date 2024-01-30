package com.example.vlak_app_test.ui.train_info

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vlak_app_test.models.train_info.TrainInfo
import com.example.vlak_app_test.network.TrainApi
import com.example.vlak_app_test.room.SearchedTrainInfo
import com.example.vlak_app_test.room.TrainInfoDao
import kotlinx.coroutines.launch
import java.util.Locale

sealed interface TrainInfoState {
    object Loading : TrainInfoState
    data class Success(val data: TrainInfo.TrainInfoTable) : TrainInfoState
    data class Error(val error: Throwable) : TrainInfoState
}

class TrainInfoViewModel(
    private val dao: TrainInfoDao
) : ViewModel() {
    var trainInfoState: TrainInfoState by mutableStateOf(TrainInfoState.Loading)

    private val _selectedTrain = mutableStateOf("")
    val selectedTrain: State<String> = _selectedTrain

    private val _recentSearches = mutableStateOf<List<SearchedTrainInfo>>(emptyList())
    val recentSearches: State<List<SearchedTrainInfo>> = _recentSearches

    fun setTrain(train: String) {
        _selectedTrain.value = train
        println("Set train: ..$train..")
        println("Selected train: ..${selectedTrain.value}..")
    }

    fun getTrain(): String {
        return selectedTrain.value
    }

    fun getTrainInfo(): TrainInfo.TrainInfoTable {
        return (trainInfoState as TrainInfoState.Success).data
    }

    suspend fun getRecentSearches() {
        _recentSearches.value = dao.getRecentSearches()
    }

    fun getData() {
        viewModelScope.launch {
            trainInfoState = TrainInfoState.Loading

            if (dao.getTrainInfoCount(selectedTrain.value) == 0) {
                dao.deleteOldSearches()
                dao.insert(SearchedTrainInfo(trainNumber = selectedTrain.value))
            }

            trainInfoState = try {
                val result = TrainApi.retrofitService.getTrainInfo(Locale.getDefault().language, selectedTrain.value)
                TrainInfoState.Success(result)
            } catch (e: Exception) {
                TrainInfoState.Error(e)
            }
        }
    }
}