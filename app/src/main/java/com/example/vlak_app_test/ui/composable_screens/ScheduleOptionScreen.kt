package com.example.vlak_app_test.ui.composable_screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.composables.MakeTopBar
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import com.example.vlak_app_test.ui.theme.TextDarkColor
import com.example.vlak_app_test.viewmodels.schedule.Schedule

@Composable
fun MakeScheduleOptionScreen(
    onAddToTripsButtonPressed: () -> Unit,
    onBackButtonPressed: (Int) -> Unit,
    getTrainInfo: () -> Unit,
    modifier: Modifier = Modifier,
    topBarText: String = "Schedule",
    data: Schedule.Options,
    route: String,
) {

    Column(
        Modifier.fillMaxSize()
    ) {

        MakeTopBar(
            titleText = topBarText,
            haveCancelButton = true,
            onCancelButtonPressed = onBackButtonPressed
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {

            Column(
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

                LazyColumn(content = {
                    items(data.trains.size) { index ->
                        MakeTrainOnTransfer(data = data.trains[index])
                    }
                })
            }
        }
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
private fun MakeTrainOnTransfer(
    data: Schedule.Trains,
) {
    Row (
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

        Row {
            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                Text(
                    text = data.from,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Black,
                    ),
                    modifier = Modifier
                        .padding(start = 8.dp)
                )

                Text(
                    text = data.depart,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black,
                    ),
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.train),
                    contentDescription = "Direct train",
                    modifier = Modifier
                        .size(22.dp)
                        .padding(top = 4.dp)
                )

                Text(
                    text = "${data.trainType} ${data.trainNum}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black,
                    ),
                    modifier = Modifier
                        .padding(start = 2.dp)
                )
            }
        }
    }
}