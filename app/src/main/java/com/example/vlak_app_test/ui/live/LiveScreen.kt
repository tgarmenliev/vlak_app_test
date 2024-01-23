package com.example.vlak_app_test.ui.live

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.R
import com.example.vlak_app_test.models.live.Live
import com.example.vlak_app_test.ui.error.ErrorScreen
import com.example.vlak_app_test.ui.loading.LoadingScreen
import com.example.vlak_app_test.ui.theme.BackgroundColor
import com.example.vlak_app_test.ui.theme.greenOnTime
import com.example.vlak_app_test.ui.top_bar.MakeTopBar
import com.example.vlak_app_test.ui.theme.redLate

@Composable
fun MakeLiveScreenOne(
    onCancelButton: () -> Unit,
    viewModel: LiveViewModel
) {
    when (val liveState = viewModel.liveState) {
        is LiveState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
        is LiveState.Success -> {
            Scaffold(
                topBar = {
                    MakeTopBar(
                        titleText = R.string.live,
                        haveCancelButton = true,
                        onCancelButtonPressed = onCancelButton
                    )
                }
            ) {
                MakeLiveScreen(
                    data = viewModel.getLiveInfo(),
                    modifier = Modifier.padding(it),
                )
            }
        }
        is LiveState.Error -> {
            ErrorScreen(error = liveState.error, modifier = Modifier.fillMaxSize())
            println(liveState.error)
        }
    }
}

@Composable
fun MakeLiveScreen(data: Live.LiveTable, modifier: Modifier = Modifier) {
    var selectedTrainNumber by remember {
        mutableStateOf("")
    }

    if (selectedTrainNumber != "") {
        MakeDelayDialog(
            onDismiss = { selectedTrainNumber = "" },
            data = data.trains.find { it.trainNum == selectedTrainNumber }!!
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(50.dp)
                .padding(top = 10.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = data.station,
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(20.dp)
                )
                .background(color = Color.White)
        ) {
            // Table header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.time),
                    modifier = Modifier.weight(2f),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    ),
                )
                Text(
                    text = stringResource(id = R.string.direction),
                    modifier = Modifier.weight(3f),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    ),
                )
                Text(
                    text = stringResource(id = R.string.train_number),
                    modifier = Modifier.weight(2.1f),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    ),
                )
            }

            // Table rows
            data.trains.forEach { train ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    if (train.isDelayed) {
                        Row(
                            modifier = Modifier
                                .clickable { selectedTrainNumber = train.trainNum }
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
                            Text(
                                text = "${train.time}",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = redLate,
                                    textDecoration = TextDecoration.Underline
                                ),
                            )
                        }
                    } else {
                        Row(
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
                                text = "${train.time}",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = greenOnTime,
                                ),
                            )
                        }
                    }

                    Text(
                        text = train.direction,
                        modifier = Modifier.weight(3f),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color.Black,
                        ),
                    )

                    Row(
                        modifier = Modifier
                            .weight(2.1f)
                    ) {
                        val annotatedString = buildAnnotatedString {
                            // Append the train type with a bold style
                            pushStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold))
                            append(train.type)
                            pop()

                            // Append the train number with the regular style
                            append(" ${train.trainNum}")
                        }
                        Text(
                            text = annotatedString,
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = Color.Black,
                            ),
                        )
//                        Text(
//                            text = train.trainNum,
//                            style = MaterialTheme.typography.labelLarge.copy(
//                                color = Color.Black,
//                            ),
//                        )
                    }
                }
            }
        }
    }
}