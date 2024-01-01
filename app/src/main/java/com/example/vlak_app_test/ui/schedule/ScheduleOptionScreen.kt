package com.example.vlak_app_test.ui.schedule

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeImageHeader
import com.example.vlak_app_test.ui.composables.MakeTopBar
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import com.example.vlak_app_test.ui.theme.TextDarkColor
import com.example.vlak_app_test.viewmodels.schedule.Schedule

@Composable
fun MakeScheduleOptionScreenSec(
    onAddToTripsButtonPressed: () -> Unit,
    getTrainInfo: () -> Unit,
    modifier: Modifier = Modifier,
    data: Schedule.Options,
    route: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .verticalScroll(rememberScrollState()),
    ) {

        MakeImageHeader(
            text = "Разписание",
            image = painterResource(id = R.drawable.schedule_back),
            modifier = Modifier.height(150.dp)
        )

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
                        color = TextDarkColor,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(Color.White)
                    .padding(4.dp),
            ) {
                Column(
                    modifier = Modifier
                ) {

                    Text(
                        text = route,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = TextDarkColor,
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
                        color = Color.Gray
                    )

                    if (data.numOfTransfers == 0) {
                        MakeTrainNumber(getTrainInfo = getTrainInfo, data = data.trains[0])
                    }

                    MakeTransfers(
                        transfers = data.numOfTransfers,
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                        textStyle = MaterialTheme.typography.titleMedium.copy(
                            color = Color.Black,
                        ),
                    )

                    MakeDuration(
                        duration = data.duration,
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                        textStyle = MaterialTheme.typography.titleMedium.copy(
                            color = Color.Black,
                        ),
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    for (index in data.trains.indices) {
                        MakeTrainOnTransfer(data = data.trains[index])

                        if (index != data.trains.size - 1) {
                            MakeTransferComposable(timeToWaitNext = data.trains[index].timeToWaitNext)
                        }
                    }

//                    LazyColumn(content = {
//                        items(data.trains.size) { index ->
//                            MakeTrainOnTransfer(data = data.trains[index])
//
//                            if (index != data.trains.size - 1) {
//                                MakeTransferComposable(timeToWaitNext = data.trains[index].timeToWaitNext)
//                            }
//                        }
//                    })
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                MakeButton(
                    text = "Add to trips",
                    onClick = onAddToTripsButtonPressed,
                    modifier = Modifier
                        .fillMaxWidth(0.6f),
                    colors = ButtonDefaults.buttonColors(
                        PrimaryDarkColor
                    )
                )
            }
        }
    }
}

@Composable
private fun MakeTrainOnTransfer(
    data: Schedule.Trains,
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
            Canvas(modifier = Modifier.size(20.dp), onDraw = {
                drawCircle(color = Color.Red)
            })
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Divider(
                    color = Color.Red,
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

                MakeTrainForTransfers(trainType = data.trainType, trainNum = data.trainNum, modifier = Modifier
                    .padding(end = 8.dp)
                    .fillMaxWidth()
                    .weight(1f))
            }


            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Row {
                Image(
                    painter = painterResource(id = R.drawable.duration),
                    contentDescription = "Duration",
                    modifier = Modifier
                        .size(22.dp)
                        .padding(top = 2.dp)
                )

                Text(
                    text = "${data.duration} ч.",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black,
                    ),
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
        }
    }

    Row {
        Box(modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)) {
            Canvas(modifier = Modifier.size(20.dp), onDraw = {
                drawCircle(color = Color.Red)
            })
        }
        MakeArrivalDepartureData(station = data.to, time = data.arrive)
    }
}

@Composable
private fun MakeTrainNumber(
    getTrainInfo: () -> Unit,
    data: Schedule.Trains,
) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.train),
            contentDescription = "Direct train",
            modifier = Modifier
                .size(28.dp)
                .padding(top = 4.dp)
        )

        Text(
            text = "${data.trainType} ${data.trainNum}",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.Black,
                textDecoration = TextDecoration.Underline,
            ),
            modifier = Modifier
                .padding(start = 2.dp)
                .clickable(onClick = getTrainInfo)
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
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.Black,
            ),
            modifier = Modifier
                .padding(start = 8.dp)
        )

        Text(
            text = time,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
            ),
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}

@Composable
private fun MakeTrainForTransfers(
    trainType: String,
    trainNum: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.train),
            contentDescription = "Train number",
            modifier = Modifier
                .size(22.dp)
                .padding(top = 4.dp)
        )

        Text(
            text = "$trainType $trainNum",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
            ),
            modifier = Modifier
                .padding(start = 2.dp)
        )
    }
}

@Composable
private fun MakeTransferComposable(
    modifier: Modifier = Modifier,
    timeToWaitNext: String = "0:00",
) {
    Column(
        modifier = modifier
    ) {
        MakeDashedLine()

        Text(text = "Време за изчакване: $timeToWaitNext ч.",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
            ),
            modifier = Modifier
                .padding(start = 16.dp)
        )

        MakeDashedLine()
    }
}

@Composable
private fun MakeDashedLine() {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Box(
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp)
    ) {
        Canvas(
            Modifier
                .fillMaxWidth(0.5f)
                .height(10.dp)
        ) {
            drawLine(
                color = Color.Red,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                pathEffect = pathEffect,
                strokeWidth = 10f
            )
        }
    }
}