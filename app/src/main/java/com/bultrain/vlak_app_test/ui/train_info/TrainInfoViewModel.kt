package com.bultrain.vlak_app_test.ui.train_info

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bultrain.vlak_app_test.models.train_info.TrainInfo
import com.bultrain.vlak_app_test.network.TrainApi
import com.bultrain.vlak_app_test.room.SearchedTrainInfo
import com.bultrain.vlak_app_test.room.TrainInfoDao
import com.bultrain.vlak_app_test.room.TripTrains
import com.bultrain.vlak_app_test.room.TripTrainsDao
import kotlinx.coroutines.launch
import java.util.Locale

sealed interface TrainInfoState {
    data object Loading : TrainInfoState
    data class Success(val data: TrainInfo.TrainInfoTable) : TrainInfoState
    data class SuccessNumbers(val data: List<String>) : TrainInfoState
    data class Error(val error: Throwable) : TrainInfoState
}

class TrainInfoViewModel(
    private val dao: TrainInfoDao,
    private val tripTrainsDao: TripTrainsDao
) : ViewModel() {
    var trainInfoState: TrainInfoState by mutableStateOf(TrainInfoState.Loading)

    private val _selectedOption = mutableStateOf(TrainInfoOption("", ""))
    private val selectedOption: State<TrainInfoOption> = _selectedOption

    private val _recentSearches = mutableStateOf<List<SearchedTrainInfo>>(emptyList())
    val recentSearches: State<List<SearchedTrainInfo>> = _recentSearches

    private val _canDownload = mutableStateOf(true)
    val canDownload: State<Boolean> = _canDownload

    fun setOption(trainNumber: String, date: String) {
        _selectedOption.value = TrainInfoOption(trainNumber, date)
    }

    fun getTrainInfo(): TrainInfo.TrainInfoTable {
        return (trainInfoState as TrainInfoState.Success).data
    }

    suspend fun getRecentSearches() {
        _recentSearches.value = dao.getRecentSearches()
    }

    fun setCanDownload(value: Boolean) {
        _canDownload.value = value
    }

    fun getCanDownload(): Boolean {
        return canDownload.value
    }

    fun insertTripTrain() {
        viewModelScope.launch {
            val trainInfo = (trainInfoState as TrainInfoState.Success).data
            tripTrainsDao.deleteTripTrainsByTrainNumber(trainInfo.trainNumber)
            tripTrainsDao.insert(
                TripTrains(
                    trainType = trainInfo.trainType,
                    trainNumber = trainInfo.trainNumber,
                    date = trainInfo.date,
                    toBeShown = true,
                    stations = trainInfo.stations
                )
            )
        }
    }

    fun deleteTripTrain(trainNum: String) {
        viewModelScope.launch {
            tripTrainsDao.deleteTripTrainsByTrainNumber(trainNum)
        }
    }

    fun getAllShownNumbers() {
        viewModelScope.launch {
            trainInfoState = TrainInfoState.Loading

            trainInfoState = try {
                val result = tripTrainsDao.getTripTrainsToBeShown().map { it.trainNumber }
                TrainInfoState.SuccessNumbers(result)
            } catch (e: Exception) {
                TrainInfoState.Error(e)
            }
        }
    }

    fun getTrainInfoByNumber(trainNum: String) {
        viewModelScope.launch {
            trainInfoState = TrainInfoState.Loading

            trainInfoState = try {
                val result = tripTrainsDao.getTripTrainsByTrainNumber(trainNum)
                if (result != null) {
                    TrainInfoState.Success(
                        TrainInfo.TrainInfoTable(
                            trainType = result.trainType,
                            trainNumber = result.trainNumber,
                            date = result.date,
                            stations = result.stations
                        )
                    )
                } else {
                    val newResult = TrainApi.retrofitService.getTrainInfo(Locale.getDefault().language, trainNum, selectedOption.value.date)
                    TrainInfoState.Success(newResult)
                }
            } catch (e: Exception) {
                TrainInfoState.Error(e)
            }
        }
    }

    fun getDataWithoutSaveSearch() {
        viewModelScope.launch {
            trainInfoState = TrainInfoState.Loading

            trainInfoState = try {
                val result = TrainApi.retrofitService.getTrainInfo(Locale.getDefault().language, selectedOption.value.trainNumber, selectedOption.value.date)
                TrainInfoState.Success(result)
            } catch (e: Exception) {
                TrainInfoState.Error(e)
            }
        }
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