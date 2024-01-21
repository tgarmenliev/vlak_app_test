package com.example.vlak_app_test.ui.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vlak_app_test.R
import com.example.vlak_app_test.models.schedule.Schedule
import com.example.vlak_app_test.ui.loading.LoadingScreen
import com.example.vlak_app_test.ui.top_bar.MakeTopBar
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import com.example.vlak_app_test.ui.theme.TextDarkColor

@Composable
fun MakeScheduleScreen(
    viewModel: ScheduleViewModel,
    onCancelButton: () -> Unit,
    navController: NavController,
) {
    when(val scheduleState = viewModel.scheduleState) {
        is ScheduleState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
        is ScheduleState.Success -> {
            Scaffold(
                topBar = {
                    MakeTopBar(
                        titleText = R.string.schedule,
                        haveCancelButton = true,
                        onCancelButtonPressed = onCancelButton
                    )
                }
            ) {
                MakeScheduleScreenSec(
                    data = viewModel.getScheduleInfo(),
                    modifier = Modifier.padding(it),
                    onClickOption = { index ->
                        viewModel.setOptionIndex(index)
                        navController.navigate("schedule_option_screen")
                    }
                )
            }
        }
        is ScheduleState.Error -> {
            Text(text = "Error: ${scheduleState.error}")
        }
    }

}

@Composable
fun MakeScheduleScreenSec(
    modifier: Modifier = Modifier,
    data: Schedule.ScheduleTable,
    onClickOption: (Int) -> Unit = {},
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "${stringResource(id = R.string.trains)}:",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.Black,
                )
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        )
        {

            // Table rows
            items(data.options) { option ->
                val routeMaker = RouteMaker(option.trains)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onClickOption(data.options.indexOf(option))
                        }
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            width = 2.dp,
                            color = TextDarkColor,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(PrimaryDarkColor),
                ) {

                    Box(
                        modifier = Modifier
                            .width(15.dp)
                            .weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(24f)
                            .background(Color.White)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {

                            Text(
                                text = routeMaker,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = TextDarkColor,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .padding(8.dp)
                            )

                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Color.Gray)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                MakeDepartArriveTime(option = option)

                                MakeDuration(duration = option.duration)
                            }

                            MakeTransfers(transfers = option.numOfTransfers, modifier = Modifier.padding(start = 12.dp, bottom = 8.dp))

                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

private fun RouteMaker(trains: List<Schedule.Trains>): String {
    var route = ""
    for (i in trains.indices) {

        route += trains[i].from + " - "

        if (i == trains.size - 1) {
            route += trains[i].to
            break
        }
    }
    return route
}

@Composable
fun MakeDepartArriveTime(
    option: Schedule.Options,
    modifier: Modifier = Modifier
) {
    Row {
        Text(
            text = option.departureTime,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.next),
            contentDescription = "Arrow",
            modifier = Modifier
                .size(24.dp)
        )

        Text(
            text = option.arrivalTime,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(start = 4.dp)
        )
    }
}

@Composable
fun MakeDuration(
    duration: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        color = Color.Black,
    ),
) {
    Row(
        modifier = modifier,
    ) {

        Image(
            painter = painterResource(id = R.drawable.duration),
            contentDescription = "Duration",
            modifier = Modifier
                .size(26.dp)
                .padding(top = 2.dp)
        )

        Text(
            text = "${duration} ч.",
            style = textStyle,
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
        )
    }
}

@Composable
fun MakeTransfers(
    transfers: Int = 0,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        color = Color.Black,
    ),
) {
    Row(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = R.drawable.transfer),
            contentDescription = "Transfers",
            modifier = Modifier
                .size(26.dp)
                .padding(top = 2.dp)
        )

        Text(
            text =
            if (transfers == 0) {
                " " + stringResource(id = R.string.direct_train)
            } else if (transfers == 1) {
                " $transfers ${stringResource(id = R.string.one_transfer)}"
            } else {
                " $transfers ${stringResource(id = R.string.many_transfers)}"
            },
            style = textStyle,
        )
    }
}