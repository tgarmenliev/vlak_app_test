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
    data object Loading : TrainInfoState
    data class Success(val data: TrainInfo.TrainInfoTable) : TrainInfoState
    data class Error(val error: Throwable) : TrainInfoState
}

class TrainInfoViewModel(
    private val dao: TrainInfoDao
) : ViewModel() {
    var trainInfoState: TrainInfoState by mutableStateOf(TrainInfoState.Loading)

    private val _selectedOption = mutableStateOf(TrainInfoOption("", ""))
    private val selectedOption: State<TrainInfoOption> = _selectedOption

    private val _recentSearches = mutableStateOf<List<SearchedTrainInfo>>(emptyList())
    val recentSearches: State<List<SearchedTrainInfo>> = _recentSearches

    fun setOption(trainNumber: String, date: String) {
        _selectedOption.value = TrainInfoOption(trainNumber, date)
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

            if (dao.getTrainInfoCount(selectedOption.value.trainNumber) == 0) {
                dao.deleteOldSearches()
                dao.insert(SearchedTrainInfo(trainNumber = selectedOption.value.trainNumber))
            }

            trainInfoState = try {
                val result = TrainApi.retrofitService.getTrainInfo(Locale.getDefault().language, selectedOption.value.trainNumber, selectedOption.value.date)
                TrainInfoState.Success(result)
            } catch (e: Exception) {
                TrainInfoState.Error(e)
            }
        }
    }
}

data class TrainInfoOption(
    val trainNumber: String,
    val date: String
)