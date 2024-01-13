package com.example.vlak_app_test.ui.train_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import com.example.vlak_app_test.viewmodels.train_info.TrainInfo.TrainInfoTable
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.top_bar.MakeTopBar

@Composable
fun MakeTrainInfo(data: TrainInfoTable, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize())
    {

        Image(
            painter = painterResource(id = R.drawable.tr_info_back),
            contentDescription = "Background image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        Column(
            Modifier.fillMaxSize()
        ) {

            MakeTopBar(
                titleText = R.string.search_train_info,
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
                        text = "${data.trainType} ${data.trainNum}",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Color.White,
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
                        .border(
                            width = 3.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(20.dp)
                        )
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
                                    text = "Station",
                                    modifier = Modifier.weight(3f),
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                                )
                                Text(
                                    text = "Arrive",
                                    modifier = Modifier.weight(1.3f),
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                                )
                                Text(
                                    text = "Depart",
                                    modifier = Modifier.weight(1.3f),
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }
                        }

                        // Table rows
                        items(data.stations) { trainInfo ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            ) {
                                Text(
                                    text = trainInfo.station,
                                    modifier = Modifier.weight(3f),
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Normal)
                                )
                                Text(
                                    text = trainInfo.arrive,
                                    modifier = Modifier.weight(1.3f),
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Normal)
                                )
                                Text(
                                    text = trainInfo.depart,
                                    modifier = Modifier.weight(1.3f),
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Normal)
                                )
                            }
                        }
                    }
                }

                MakeButton(
                    text = R.string.home,
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
}