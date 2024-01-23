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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.R
import com.example.vlak_app_test.models.train_info.TrainInfo
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.error.ErrorScreen
import com.example.vlak_app_test.ui.loading.LoadingScreen
import com.example.vlak_app_test.ui.theme.BackgroundColor
import com.example.vlak_app_test.ui.top_bar.MakeTopBar

@Composable
fun MakeTrainInfoScreen(
    viewModel: TrainInfoViewModel,
    onCancelButton: () -> Unit,
) {
    when (val trainInfoState = viewModel.trainInfoState) {
        is TrainInfoState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
        is TrainInfoState.Success -> {
            Scaffold(
                topBar = {
                    MakeTopBar(
                        titleText = R.string.train_info,
                        haveCancelButton = true,
                        onCancelButtonPressed = onCancelButton,
                    )
                }
            ) {
                MakeTrainInfo(
                    data = viewModel.getTrainInfo(),
                    modifier = Modifier.padding(it)
                )
            }
        }
        is TrainInfoState.Error -> {
            ErrorScreen(error = trainInfoState.error, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun MakeTrainInfo(
    data: TrainInfo.TrainInfoTable,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .background(BackgroundColor)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "${data.trainType} ${data.trainNumber}",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
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
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.station),
                        modifier = Modifier.weight(3f),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = stringResource(id = R.string.arrives),
                        modifier = Modifier.weight(1.5f),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = stringResource(id = R.string.departs),
                        modifier = Modifier.weight(1.5f),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                }

                // Table rows
                data.stations?.let { stations ->
                    for (trainInfo in stations) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 4.dp)
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
        }
    }
}


