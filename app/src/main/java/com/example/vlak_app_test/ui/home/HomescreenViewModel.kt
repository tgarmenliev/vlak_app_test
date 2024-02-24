package com.example.vlak_app_test.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vlak_app_test.models.schedule.Schedule
import com.example.vlak_app_test.room.Trip
import com.example.vlak_app_test.room.TripDao
import com.example.vlak_app_test.room.TripHeading
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
    private val dao: TripDao
) : ViewModel() {
    var homeState: HomeState by mutableStateOf(HomeState.Loading)

    var tripState: TripState by mutableStateOf(TripState.Loading)

    private val _recentTrips = mutableStateOf<List<TripHeading>>(emptyList())
    val recentTrips: State<List<TripHeading>> = _recentTrips

    fun insertTrip(trip: Schedule.Options, route: String) {
        viewModelScope.launch {
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
            dao.deleteOldTrips(date)
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
            dao.deleteTripById(id)
            getTrips()
        }
    }
}