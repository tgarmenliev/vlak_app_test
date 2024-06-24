package com.bultrain.vlak_app_test.ui.schedule

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bultrain.vlak_app_test.R
import com.bultrain.vlak_app_test.room.SearchedSchedule
import com.bultrain.vlak_app_test.room.SearchedTrainInfo
import com.bultrain.vlak_app_test.ui.composables.MakeButton
import com.bultrain.vlak_app_test.ui.composables.MakeDatePickerDialog
import com.bultrain.vlak_app_test.ui.composables.MakeImageHeader
import com.bultrain.vlak_app_test.ui.composables.MakeSimpleInputField
import com.bultrain.vlak_app_test.ui.composables.MakeStationInputField
import com.bultrain.vlak_app_test.ui.train_info.TrainInfoViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun MakeScheduleSearchScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ScheduleViewModel,
    trainInfoViewModel: TrainInfoViewModel,
    isKeyboardVisible: MutableState<Boolean>
) {
    var stationOne by rememberSaveable { mutableStateOf("") }
    var stationTwo by rememberSaveable { mutableStateOf("") }
    var trainInfo by remember { mutableStateOf("") }

    val formatter = SimpleDateFormat("yyyy-MM-dd")

    var date by rememberSaveable {
        mutableStateOf(formatter.format(Date(Calendar.getInstance().timeInMillis)))
    }
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var recentSearched by rememberSaveable { mutableStateOf<List<SearchedSchedule>>(emptyList()) }
    var trainRecentSearched by rememberSaveable { mutableStateOf<List<SearchedTrainInfo>>(emptyList()) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getRecentSearches()
        recentSearched = viewModel.recentSearches.value
    }

    LaunchedEffect(Unit) {
        trainInfoViewModel.getRecentSearches()
        trainRecentSearched = trainInfoViewModel.recentSearches.value
    }

    val scrollState = rememberScrollState()

    // FocusRequester is used to request focus on the input field when it is clicked
    val focusRequester = FocusRequester()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            MakeImageHeader(
                text = R.string.schedule,
                image = painterResource(id = R.drawable.schedule_back),
                modifier = Modifier.height(200.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clickable {
                                val temp = stationOne
                                stationOne = stationTwo
                                stationTwo = temp
                            }
                            .align(Alignment.End)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.Transparent, shape = CircleShape)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    shape = CircleShape
                                )
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.swap),
                            contentDescription = "Swap stations",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center)
                        )
                    }


                    MakeStationInputField(
                        station = stationOne,
                        onStationSelected = {
                            stationOne = it
                        },
                        hintText = R.string.start_searching,
                        labelText = R.string.from_station,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .align(Alignment.CenterHorizontally)
                            .focusRequester(focusRequester),
                        isKeyboardVisible = isKeyboardVisible
                    )

                    MakeStationInputField(
                        station = stationTwo,
                        onStationSelected = {
                            stationTwo = it
                        },
                        hintText = R.string.start_searching,
                        labelText = R.string.to_station,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .align(Alignment.CenterHorizontally)
                            .focusRequester(focusRequester),
                        isKeyboardVisible = isKeyboardVisible
                    )

                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 3.dp, bottom = 2.dp)
                            .align(Alignment.Start),
                        text = stringResource(id = R.string.choose_date),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        fontWeight = FontWeight.Medium
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Button(
                            onClick = { showDatePicker = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .focusRequester(focusRequester),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = date,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Left
                            )
                        }
                    }

                    if (showDatePicker) {
                        MakeDatePickerDialog(
                            onDateSelected = { date = it },
                            onDismiss = { showDatePicker = false }
                        )
                    }

                    MakeButton(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 16.dp)
                            .fillMaxWidth(0.6f)
                            .height(50.dp)
                            .align(Alignment.CenterHorizontally),
                        text = R.string.search
                    ) {
                        if (!viewModel.checkIfStationExists(stationOne) || !viewModel.checkIfStationExists(
                                stationTwo
                            )
                        ) {
                            Toast.makeText(
                                context,
                                R.string.station_not_exist,
                                Toast.LENGTH_SHORT
                            ).show()
                            stationOne = ""
                            stationTwo = ""
                        } else {
                            viewModel.setOption(stationOne, stationTwo, date)
                            viewModel.getData()
                            navController.navigate("schedule_screen")
                        }
                    }

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                            .padding(vertical = 10.dp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 4.dp)
                            .align(Alignment.Start),
                        text = stringResource(id = R.string.recent_searches),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    if (recentSearched.isEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 10.dp)
                                .align(Alignment.Start),
                            text = stringResource(id = R.string.no_recent_searches),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .padding(end = 8.dp, bottom = 10.dp)
                                .fillMaxWidth(),
                        ) {
                            recentSearched.forEach {
                                Text(
                                    text = "${it.fromStation} - ${it.toStation}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .clickable {
                                            stationOne = it.fromStation
                                            stationTwo = it.toStation
                                        },
                                    textDecoration = TextDecoration.Underline,
                                )
                            }
                        }
                    }

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                            .padding(vertical = 10.dp)
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    ) {

                        MakeSimpleInputField(
                            titleText = trainInfo,
                            hintText = R.string.start_searching,
                            keyboardType = KeyboardType.Decimal,
                            onValueChange = {
                                trainInfo = it
                            },
                            labelText = R.string.search_by_train_number,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .weight(3.5f)
                                .focusRequester(focusRequester),
                            isKeyboardVisible = isKeyboardVisible
                        )

                        Button(
                            onClick = {
                                if (trainInfo.isEmpty() || trainInfo.length < 3 || trainInfo.length > 5) {
                                    Toast.makeText(
                                        context,
                                        R.string.empty_train_info,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    trainInfo = ""
                                } else {
                                    trainInfoViewModel.setCanDownload(true)
                                    trainInfoViewModel.setOption(trainInfo, date)
                                    trainInfoViewModel.getData()
                                    trainInfo = ""
                                    navController.navigate("train_info")
                                }
                            },
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 32.dp, start = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.train),
                                contentDescription = "Train icon",
                                modifier = Modifier
                                    .size(42.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        trainRecentSearched.forEach {
                            Text(
                                text = it.trainNumber,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .clickable {
                                        trainInfo = it.trainNumber
                                    },
                                textDecoration = TextDecoration.Underline,
                            )
                        }
                    }
                }
            }
        }
    }
}