package com.example.vlak_app_test.ui.composable_screens

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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeTopBar
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import com.example.vlak_app_test.ui.theme.TextDarkColor
import com.example.vlak_app_test.ui.theme.greenOnTime
import com.example.vlak_app_test.ui.theme.redLate
import com.example.vlak_app_test.viewmodels.schedule.Schedule

@Composable
fun MakeScheduleScreen(
    onBackButtonPressed: (Int) -> Unit,
    modifier: Modifier = Modifier,
    backgroundImage: Painter? = null,
    topBarText: String = "Schedule",
    data: Schedule.ScheduleTable
) {
    Box(modifier = Modifier.fillMaxSize())
    {

        backgroundImage?.let {
            androidx.compose.foundation.Image(
                painter = it,
                contentDescription = "Background image",
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Column(
            Modifier.fillMaxSize()
        ) {

            MakeTopBar(
                titleText = topBarText,
                haveCancelButton = true,
                onCancelButtonPressed = onBackButtonPressed
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = "Trains:",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Color.White,
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
                                .clickable { /*TODO*/ }
                                .clip(RoundedCornerShape(20.dp))
//                                .border(
//                                    width = 2.dp,
//                                    color = Color.Black,
//                                    shape = RoundedCornerShape(20.dp)
//                                )
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

                                        Row() {

                                            Image(
                                                painter = painterResource(id = R.drawable.duration),
                                                contentDescription = "Duration",
                                                modifier = Modifier
                                                    .size(24.dp)
                                            )

                                            Text(
                                                text = option.duration,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                modifier = Modifier
                                                    .padding(start = 4.dp, end = 4.dp)
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier
                                            .padding(start = 12.dp, top = 8.dp, bottom = 8.dp),
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.transfer),
                                            contentDescription = "Transfers",
                                            modifier = Modifier
                                                .size(24.dp)
                                        )

                                        Text(
                                            text =
                                                if (option.numOfTransfers == 0) {
                                                    " Директен влак"
                                                } else if (option.numOfTransfers == 1) {
                                                    " " + option.numOfTransfers.toString() + " прекачване"
                                                } else {
                                                    " " + option.numOfTransfers.toString() + " прекачвания"
                                                }
                                            ,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = Color.Black,
                                            ),
                                        )
                                    }

                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
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