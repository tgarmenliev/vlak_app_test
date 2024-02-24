package com.example.vlak_app_test.ui.schedule

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vlak_app_test.R
import com.example.vlak_app_test.models.schedule.Schedule
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.top_bar.MakeTopBar
import com.example.vlak_app_test.ui.train_info.TrainInfoViewModel

@Composable
fun MakeScheduleOptionScreen(
    onAddToTripsButtonPressed: (Schedule.Options, String) -> Unit,
    trainInfoViewModel: TrainInfoViewModel,
    onCancelButton: () -> Unit,
    viewModel: ScheduleViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            MakeTopBar(
                titleText = R.string.schedule,
                haveCancelButton = true,
                onCancelButtonPressed = onCancelButton
            )
        }
    ) {
        MakeScheduleOptionScreenSec(
            onAddToTripsButtonPressed = onAddToTripsButtonPressed,
            getTrainInfo = { trainNum, date ->
                trainInfoViewModel.setOption(trainNum, date)
                trainInfoViewModel.getData()
                navController.navigate("train_info")
            },
            data = viewModel.getDataOption(),
            route = viewModel.getRoute(),
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun MakeScheduleOptionScreenSec(
    onAddToTripsButtonPressed: (Schedule.Options, String) -> Unit,
    getTrainInfo: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    data: Schedule.Options,
    route: String,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(4.dp),
            ) {
                Column(
                    modifier = Modifier
                ) {

                    Text(
                        text = route,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    if (data.numOfTransfers == 0) {
                        MakeTrainNumber(getTrainInfo = getTrainInfo, data = data.trains[0])
                    }

                    MakeTransfers(
                        transfers = data.numOfTransfers,
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                        textStyle = MaterialTheme.typography.titleMedium,
                    )

                    MakeDuration(
                        duration = data.duration,
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                        textStyle = MaterialTheme.typography.titleMedium,
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    for (index in data.trains.indices) {
                        MakeTrainOnTransfer(data = data.trains[index], getTrainInfo = getTrainInfo)

                        if (index != data.trains.size - 1) {
                            MakeTransferComposable(timeToWaitNext = data.trains[index].timeToWaitNext)
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            val addedText = stringResource(id = R.string.added_to_trips)

            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                MakeButton(
                    modifier = Modifier
                        .fillMaxWidth(0.6f),
                    text = R.string.add_to_trips,
                    onClick = {
                        Toast.makeText(
                            context,
                            addedText,
                            Toast.LENGTH_SHORT
                        ).show()
                        onAddToTripsButtonPressed(data, route)
                    }
                )
            }
        }
    }
}

@Composable
private fun Circle(
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Canvas(modifier = Modifier.size(20.dp), onDraw = {
        drawCircle(color = color)
    })
}

@Composable
fun MakeTrainOnTransfer(
    data: Schedule.Trains,
    getTrainInfo: (String, String) -> Unit,
    hasClickableTrainInfo: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp)
            .height(IntrinsicSize.Max) // Позволява всички неща вътре да са с еднаква височина
    ) {
        Column(
            modifier = Modifier
                .padding(top = 4.dp)
        ) {
            Circle()
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Divider(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                )
            }
        }

        Column (
            modifier = Modifier.fillMaxWidth()
        ) {

            Row {
                MakeArrivalDepartureData(station = data.from, time = data.depart, modifier = Modifier.weight(2f))

                MakeTrainForTransfers(
                    trainType = data.trainType,
                    trainNum = data.trainNumber,
                    date = data.departDate,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .fillMaxWidth()
                        .weight(1f),
                    getTrainInfo = getTrainInfo,
                    hasClickableTrainInfo = hasClickableTrainInfo
                )
            }


            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Row {
                Icon(
                    painter = painterResource(id = R.drawable.duration),
                    contentDescription = "Duration",
                    modifier = Modifier
                        .size(22.dp)
                        .padding(top = 2.dp),
                )

                Text(
                    text = "${data.duration} ${stringResource(id = R.string.hours_short)}",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
        }
    }

    Row {
        Box(modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)) {
            Circle()
        }
        MakeArrivalDepartureData(station = data.to, time = data.arrive)
    }
}

@Composable
private fun MakeTrainNumber(
    getTrainInfo: (String, String) -> Unit,
    data: Schedule.Trains,
) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.train),
            contentDescription = "Direct train",
            modifier = Modifier
                .size(28.dp)
                .padding(top = 4.dp)
        )

        Text(
            text = "${data.trainType} ${data.trainNumber}",
            style = MaterialTheme.typography.titleLarge.copy(
                textDecoration = TextDecoration.Underline,
            ),
            modifier = Modifier
                .padding(start = 2.dp)
                .clickable { getTrainInfo(data.trainNumber, data.departDate) }
        )
    }
}

@Composable
private fun MakeArrivalDepartureData(
    station: String,
    time: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = station,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(start = 8.dp)
        )

        Text(
            text = time,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}

@Composable
private fun MakeTrainForTransfers(
    trainType: String,
    trainNum: String,
    date: String,
    modifier: Modifier = Modifier,
    getTrainInfo: (String, String) -> Unit,
    hasClickableTrainInfo: Boolean = true
) {
    Row(
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.train),
            contentDescription = "Train number",
            modifier = Modifier
                .size(22.dp)
                .padding(top = 4.dp)
        )

        Text(
            text = "$trainType $trainNum",
            style = MaterialTheme.typography.bodyMedium.copy(
                textDecoration = TextDecoration.Underline,
            ),
            modifier = Modifier
                .padding(start = 2.dp)
                .clickable {
                    if (hasClickableTrainInfo) {
                        getTrainInfo(trainNum, date)
                    }
                }
        )
    }
}

@Composable
fun MakeTransferComposable(
    modifier: Modifier = Modifier,
    timeToWaitNext: String = "0:00",
) {
    Column(
        modifier = modifier
    ) {
        MakeDashedLine()

        Text(text = "${stringResource(id = R.string.time_to_wait_next)} " +
                "$timeToWaitNext " +
                stringResource(id = R.string.hours_short),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 16.dp)
        )

        MakeDashedLine()
    }
}

@Composable
private fun Line(
    color: Color = MaterialTheme.colorScheme.primary,
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Canvas(
        Modifier
            .fillMaxWidth(0.5f)
            .height(10.dp)
    ) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
            strokeWidth = 10f
        )
    }
}

@Composable
private fun MakeDashedLine() {
    Box(
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp)
    ) {
        Line()
    }
}