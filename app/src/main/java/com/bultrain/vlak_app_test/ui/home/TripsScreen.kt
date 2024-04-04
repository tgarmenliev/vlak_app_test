package com.bultrain.vlak_app_test.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bultrain.vlak_app_test.R
import com.bultrain.vlak_app_test.room.Trip
import com.bultrain.vlak_app_test.ui.composables.MakeDeleteTripDialog
import com.bultrain.vlak_app_test.ui.error.ErrorScreen
import com.bultrain.vlak_app_test.ui.loading.LoadingScreen
import com.bultrain.vlak_app_test.ui.schedule.MakeTrainOnTransfer
import com.bultrain.vlak_app_test.ui.schedule.MakeTransferComposable
import com.bultrain.vlak_app_test.ui.schedule.MakeTransfers
import com.bultrain.vlak_app_test.ui.top_bar.MakeTopBar

@Composable
fun MakeTripsScreen(
    viewModel: HomescreenViewModel,
    onBackSelected: () -> Unit = {}
) {
    when (val tripsState = viewModel.tripState) {
        is TripState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
        is TripState.Success -> {
             Scaffold(
                 topBar = {
                     MakeTopBar(
                         titleText = R.string.my_trips,
                         haveCancelButton = true,
                         onCancelButtonPressed = onBackSelected
                     )
                 }
             ) {
                TripsScreen(
                    data = tripsState.data,
                    modifier = Modifier.padding(it),
                    onDeleteTrip = { id -> viewModel.deleteTripById(id) }
                )
            }
        }
        is TripState.Error -> {
            ErrorScreen(error = tripsState.error, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun TripsScreen(
    modifier: Modifier = Modifier,
    data: List<Trip> = listOf(),
    onDeleteTrip: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        data.forEach { trip ->
            TripCard(
                trip = trip,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                onDeleteTrip = onDeleteTrip
            )
        }
    }
}

@Composable
fun TripCard(
    trip: Trip,
    modifier: Modifier = Modifier,
    onDeleteTrip: (Int) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = trip.departureDate,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                )

                Text(
                    text = trip.route,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                )
            }

            IconButton(onClick = { showDialog = true }) {//onDeleteTrip(trip.id) }) {
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = "delete trip",
                    modifier = Modifier
                        .size(22.dp)
                        .padding(end = 4.dp)
                        .align(Alignment.Top)
                )
            }

            if (showDialog) {
                MakeDeleteTripDialog(
                    onDismiss = { showDialog = false },
                    onDelete = { onDeleteTrip(trip.id) }
                )
            }
        }

        MakeTransfers(
            transfers = trip.numOfTransfers,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)
        )

        Row {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = trip.departureTime,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.right),
                    contentDescription = "time arrow",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = trip.arrivalTime,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.duration),
                    contentDescription = "duration",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "${trip.duration} ${stringResource(id = R.string.hours_short)}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp),
                    fontWeight = FontWeight.Light
                )
            }
        }

        if (isExpanded) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.secondary
            )

            for (index in trip.trains.indices) {
                MakeTrainOnTransfer(data = trip.trains[index], getTrainInfo = {_, _ -> run {} }, hasClickableTrainInfo = false)

                if (index != trip.trains.size - 1) {
                    MakeTransferComposable(timeToWaitNext = trip.trains[index].timeToWaitNext)
                }
            }
        }

        Icon(
            painter = painterResource(id = (if (isExpanded) R.drawable.arrow_up else R.drawable.arrow_down)),
            contentDescription = "see more",
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    isExpanded = !isExpanded
                }
        )
    }
}