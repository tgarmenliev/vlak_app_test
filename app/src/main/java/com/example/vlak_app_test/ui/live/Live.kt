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
import androidx.compose.material3.MaterialTheme
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
import com.example.vlak_app_test.ui.theme.greenOnTime
import com.example.vlak_app_test.ui.theme.redLate
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
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.Black,
                    )
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
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
                                .padding(3.dp)
                        ) {
                            Text(
                                text = "Time",
                                modifier = Modifier.weight(2f),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold
                                ),
                            )
                            Text(
                                text = "Direction",
                                modifier = Modifier.weight(3f),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold
                                ),
                            )
                            Text(
                                text = "Train #",
                                modifier = Modifier.weight(1.5f),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold
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
                                .padding(3.dp)
                        ) {
                            if (train.isDelayed) {
                                Row (
                                    modifier = Modifier
                                        .clickable { /*TODO*/ }
                                        .weight(2f)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.warning),
                                        contentDescription = "Delayed icon",
                                        modifier = Modifier
                                            .size(24.dp)
                                            .padding(top = 4.dp),
                                        tint = redLate
                                    )
                                    Text(text = " ")
                                    Text(
                                        text = "${train.time}",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            color = redLate,
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
                                        painter = painterResource(id = R.drawable.check),
                                        contentDescription = "On time icon",
                                        modifier = Modifier
                                            .size(24.dp)
                                            .padding(top = 4.dp),
                                        tint = greenOnTime
                                    )
                                    Text(
                                        text = " ${train.time}",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            color = greenOnTime,
                                        ),
                                    )
                                }
                            }

                            Text(
                                text = train.direction,
                                modifier = Modifier.weight(3f),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = Color.Black,
                                ),
                            )

                            Row (
                                modifier = Modifier
                                    .weight(1.5f)
                            ) {
                                Text(
                                    text = train.trainType,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = Color.Black,
                                    ),
                                )
                                Text(
                                    text = train.trainNum,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = Color.Black,
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