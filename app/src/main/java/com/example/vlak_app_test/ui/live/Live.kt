package com.example.vlak_app_test.ui.live

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import com.example.vlak_app_test.viewmodels.train_info.TrainInfo.TrainInfoTable
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeTopBar
import com.example.vlak_app_test.viewmodels.live.Live.LiveTable

@Composable
fun MakeLiveScreen(data: LiveTable, modifier: Modifier = Modifier) {

    Column(
        Modifier.fillMaxSize()
    ) {

        MakeTopBar(
            titleText = "Live",
            haveCancelButton = false
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(40.dp)
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = data.station,
                    style = TextStyle(
                        fontSize = 26.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(width = 5.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
                    .background(color = Color.White)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
                {
                    // Table header
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Text(
                                text = "Time",
                                modifier = Modifier.weight(2f),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                            Text(
                                text = "Direction",
                                modifier = Modifier.weight(3f),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                            Text(
                                text = "Train num",
                                modifier = Modifier.weight(2f),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                        }
                    }

                    // Table rows
                    items(data.trains) { train ->
                        Spacer(modifier = Modifier.padding(2.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            if (train.isDelayed) {
                                Row (
                                    modifier = Modifier
                                        .clickable { /*TODO*/ }
                                        .weight(2f)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.delayed),
                                        contentDescription = "Delayed icon",
                                        modifier = Modifier.size(24.dp),
                                        tint = Color.Red
                                    )
                                    Text(
                                        text = train.time,
                                        style = TextStyle(
                                            fontSize = 22.sp,
                                            color = Color.Red,
                                            fontWeight = FontWeight.Bold,
                                            textDecoration = TextDecoration.Underline
                                        ),
                                    )
                                }
                            } else {
                                Row (
                                    modifier = Modifier
                                        .weight(2f)
                                ) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.on_time),
                                        contentDescription = "On time icon",
                                        modifier = Modifier.size(24.dp),
                                        tint = Color.Green
                                    )
                                    Text(
                                        text = " ${train.time}",
                                        style = TextStyle(
                                            fontSize = 22.sp,
                                            color = Color.Green,
                                            fontWeight = FontWeight.Bold
                                        ),
                                    )
                                }
                            }

                            Text(
                                text = train.direction,
                                modifier = Modifier.weight(3f),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                ),
                            )

                            Row (
                                modifier = Modifier
                                    .weight(2f)
                            ) {
                                Text(
                                    text = train.trainType,
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    ),
                                )
                                Text(
                                    text = train.trainNum,
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    ),
                                )
                            }
                        }
                    }
                }
            }

            MakeButton(
                text = "Home",
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 65.dp, end = 65.dp, bottom = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    PrimaryDarkColor
                ),
                enabled = true
            )
        }
    }
}