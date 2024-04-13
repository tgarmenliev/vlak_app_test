package com.bultrain.vlak_app_test.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bultrain.vlak_app_test.models.schedule.Schedule
import com.bultrain.vlak_app_test.models.train_info.TrainInfo
import com.bultrain.vlak_app_test.network.TrainApi
import com.bultrain.vlak_app_test.room.Trip
import com.bultrain.vlak_app_test.room.TripDao
import com.bultrain.vlak_app_test.room.TripHeading
import com.bultrain.vlak_app_test.room.TripTrains
import com.bultrain.vlak_app_test.room.TripTrainsDao
import com.bultrain.vlak_app_test.ui.train_info.TrainInfoState
import com.bultrain.vlak_app_test.ui.train_info.TrainInfoViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed interface HomeState {
    data object Loading : HomeState
    data class Success(val data: List<TripHeading>) : HomeState
    data class Error(val error: Throwable) : HomeState
}

sealed interface TripState {
    data object Loading : TripState
    data class Success(val data: List<Trip>) : TripState
    data class Error(val error: Throwable) : TripState
}

class HomescreenViewModel(
    private val dao: TripDao,
    private val tripTrainsDao: TripTrainsDao,
    private val trainInfoViewModel: TrainInfoViewModel
) : ViewModel() {
    var homeState: HomeState by mutableStateOf(HomeState.Loading)

    var tripState: TripState by mutableStateOf(TripState.Loading)

    private val _recentTrips = mutableStateOf<List<TripHeading>>(emptyList())
    val recentTrips: State<List<TripHeading>> = _recentTrips

    private fun insertTrainTrips(trips: List<Schedule.Trains>) {
        viewModelScope.launch {
            trips.forEach { train ->
                if (tripTrainsDao.checkIfTripTrainsExists(train.trainNumber, train.departDate) == 0) {
                    var fetchedTrainType = train.trainType
                    val stations = try {
                        val result = TrainApi.retrofitService.getTrainInfo(Locale.getDefault().language, train.trainNumber, train.departDate)
                        fetchedTrainType = result.trainType
                        TrainInfoState.Success(result)
                    } catch (e: Exception) {
                        TrainInfoState.Error(e)
                    }.let {
                        when (it) {
                            is TrainInfoState.Success -> it.data.stations
                            else -> emptyList()
                        }
                    }
                    TripTrains(
                        trainType = fetchedTrainType,
                        trainNumber = train.trainNumber,
                        date = train.departDate,
                        stations = stations
                    ).also { tripTrainsDao.insert(it) }
                }
            }
        }
    }

    fun insertTrip(trip: Schedule.Options, route: String) {
        viewModelScope.launch {
            // Check if trip already exists, if not insert it into the database
            if (dao.checkIfTripExists(route, trip.departureDate, trip.departureTime) == 0) {
                Trip(
                    route = route,
                    duration = trip.duration,
                    departureTime = trip.departureTime,
                    arrivalTime = trip.arrivalTime,
                    departureDate = trip.departureDate,
                    arrivalDate = trip.arrivalDate,
                    numOfTransfers = trip.numOfTransfers,
                    trains = trip.trains
                ).also { dao.insert(it) }
                insertTrainTrips(trip.trains)
            }
        }
    }

    fun getRecentTrips() {
        viewModelScope.launch {
            homeState = HomeState.Loading
            _recentTrips.value = dao.getRecent3TripTitles()
            homeState = HomeState.Success(_recentTrips.value)
        }
    }

    fun getTrips() {
        viewModelScope.launch {
            tripState = TripState.Loading
            tripState = TripState.Success(dao.getTrips())
        }
    }

    private suspend fun getRecentTripsInit() {
        _recentTrips.value = dao.getRecent3TripTitles()
        homeState = HomeState.Success(_recentTrips.value)
    }

    private suspend fun deleteOldTrips() {
        viewModelScope.launch {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormat.format(Date())
            tripTrainsDao.deleteTripTrainsByDate(date)
            dao.deleteOldTrips(date)
        }
    }

    private suspend fun deleteTripTrainsByTrainNumberAndDate(trainNumber: String, date: String) {
        if (tripTrainsDao.checkIfTripTrainsExists(trainNumber, date) > 0) {
            tripTrainsDao.deleteTripTrainsByTrainNumberAndDate(trainNumber, date)
        }
    }

    fun deleteAndGetRecentTrips() {
        viewModelScope.launch {
            deleteOldTrips()
            getRecentTripsInit()
        }
    }

    fun deleteTripById(id: Int) {
        viewModelScope.launch {
            dao.getTripById(id).trains.forEach { train ->
                deleteTripTrainsByTrainNumberAndDate(train.trainNumber, train.departDate)
            }
            dao.deleteTripById(id)
            getTrips()
        }
    }

    fun getTripTrain(trainNum: String, date: String) {
        viewModelScope.launch {
            trainInfoViewModel.trainInfoState = TrainInfoState.Loading
            trainInfoViewModel.trainInfoState = try {
                val result = tripTrainsDao.getTripTrainsByTrainNumberAndDate(trainNum, date)
                if (result != null) {
                    TrainInfoState.Success(TrainInfo.TrainInfoTable(
                        trainType = result.trainType,
                        trainNumber = result.trainNumber,
                        date = result.date,
                        stations = result.stations
                    ))
                } else {
                    val newResult = TrainApi.retrofitService.getTrainInfo(Locale.getDefault().language, trainNum, date)
                    TrainInfoState.Success(newResult)
                }
            } catch (e: Exception) {
                TrainInfoState.Error(e)
            }
        }
    }
}