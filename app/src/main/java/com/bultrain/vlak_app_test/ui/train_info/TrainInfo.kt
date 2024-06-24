package com.bultrain.vlak_app_test.ui.train_info

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bultrain.vlak_app_test.R
import com.bultrain.vlak_app_test.models.train_info.TrainInfo
import com.bultrain.vlak_app_test.ui.error.ErrorScreen
import com.bultrain.vlak_app_test.ui.loading.LoadingScreen
import com.bultrain.vlak_app_test.ui.top_bar.MakeTopBar

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
                    canDownload = viewModel.getCanDownload(),
                    modifier = Modifier.padding(it),
                    downloadRoute = { viewModel.insertTripTrain() }
                )
            }
        }
        is TrainInfoState.SuccessNumbers -> {
            // TODO
        }
        is TrainInfoState.Error -> {
            ErrorScreen(error = trainInfoState.error, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun MakeTrainInfo(
    data: TrainInfo.TrainInfoTable,
    canDownload: Boolean = true,
    modifier: Modifier = Modifier,
    downloadRoute: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
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
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "${stringResource(id = R.string.for_date)} ${data.date}",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.station),
                        modifier = Modifier.weight(2.1f),
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

                data.stations.let { stations ->
                    for (trainInfo in stations) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = trainInfo.station,
                                modifier = Modifier.weight(2.1f),
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                if (canDownload) {
                    val addedText = stringResource(id = R.string.downloaded)

                    Text(
                        text = stringResource(id = R.string.download),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textDecoration = TextDecoration.Underline,
                        ),
                        modifier = Modifier
                            .clickable {
                                Toast.makeText(
                                    context,
                                    addedText,
                                    Toast.LENGTH_SHORT
                                ).show()
                                downloadRoute()
                            }

                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.warning_saved_routes),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray
                        )
                    )
                }
            }
        }
    }
}