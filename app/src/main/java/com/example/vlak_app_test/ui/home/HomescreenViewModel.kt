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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed interface HomeState {
    object Loading : HomeState
    data class Success(val data: List<TripHeading>) : HomeState
    data class Error(val error: Throwable) : HomeState
}

sealed interface TripState {
    object Loading : TripState
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

    fun getRecentTrips() {
        viewModelScope.launch {
            homeState = HomeState.Loading
            //delay(1000L)
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
}