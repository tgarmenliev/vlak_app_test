package com.example.vlak_app_test.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.R
import com.example.vlak_app_test.room.TripHeading
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeImageHeader
import com.example.vlak_app_test.ui.error.ErrorScreen
import com.example.vlak_app_test.ui.loading.LoadingScreen

@Composable
fun Homescreen(
    modifier: Modifier = Modifier,
    viewModel: HomescreenViewModel,
    onSettingsClick: () -> Unit = { },
    onClick: () -> Unit = { }
) {
    when (val homeState = viewModel.homeState) {
        is HomeState.Success -> {
            MakeHomescreen(
                modifier = modifier,
                viewModel = viewModel,
                onSettingsClick = onSettingsClick,
                onRefreshClick = { viewModel.getRecentTrips() },
                onClick = onClick
            )
        }
        is HomeState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
        is HomeState.Error -> {
            ErrorScreen(error = homeState.error)
        }
    }
}

@Composable
fun MakeHomescreen(
    modifier: Modifier = Modifier,
    viewModel: HomescreenViewModel,
    onSettingsClick: () -> Unit = { },
    onRefreshClick: () -> Unit = { },
    onClick: () -> Unit = { }
) {
    //var recentTrips by remember { mutableStateOf<List<TripHeading>>(emptyList()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        MakeImageHeader(
            text = R.string.home,
            image = painterResource(id = R.drawable.home_back2),
            modifier = Modifier.height(200.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Box(modifier = Modifier.padding(8.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp)
                ) {
                    MakeRecentTrips(
                        modifier = Modifier
                            .fillMaxWidth(),
                        recentTrips = viewModel.recentTrips.value,
                        onClick = onClick,
                        onRefreshClick = onRefreshClick
                    )
                }
            }

            MakeButton(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = R.string.settings,
                onClick = onSettingsClick
            )
        }
    }
}

@Composable
fun MakeRecentTrips(
    modifier: Modifier,
    recentTrips: List<TripHeading>,
    onClick: () -> Unit = { },
    onRefreshClick: () -> Unit = { }
) {
    if (recentTrips.isNotEmpty()) {
        Column(
            modifier = modifier
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.recent_trips),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(8.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.refresh),
                    contentDescription = "refresh",
                    modifier = Modifier
                        .size(28.dp)
                        .padding(top = 4.dp)
                        .clickable {
                            onRefreshClick()
                        }
                )
            }

            recentTrips.forEach {
                MakeRecentTrip(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    trip = it
                )
            }

            MakeButton(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = R.string.view_all_trips,
                onClick = onClick
            )
        }
    } else {
        Text(text = stringResource(id = R.string.no_added_trips))
    }
}

@Composable
fun MakeRecentTrip(modifier: Modifier, trip: TripHeading) {
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
    }
}
