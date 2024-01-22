package com.example.vlak_app_test.ui.schedule

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vlak_app_test.R
import com.example.vlak_app_test.rememberImeState
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeDatePickerDialog
import com.example.vlak_app_test.ui.composables.MakeImageHeader
import com.example.vlak_app_test.ui.composables.MakeSimpleInputField
import com.example.vlak_app_test.ui.composables.MakeStationInputField
import com.example.vlak_app_test.ui.train_info.TrainInfoViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun MakeScheduleSearchScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ScheduleViewModel,
    trainInfoViewModel: TrainInfoViewModel
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

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }

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
                modifier = Modifier.fillMaxHeight(0.3f)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    MakeStationInputField(
                        station = stationOne,
                        onStationSelected = {
                            stationOne = it
                        },
                        hintText = R.string.start_searching,
                        labelText = R.string.from_station,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .align(Alignment.CenterHorizontally)
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
                    )

                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 3.dp, bottom = 2.dp)
                            .align(Alignment.Start),
                        text = stringResource(id = R.string.choose_date),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        ),
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )

                    Button(
                        onClick = { showDatePicker = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 3.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                        )
                    ) {
                        Text(
                            text = date,
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = Color.Black
                            ),
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Left
                        )
                    }

                    if (showDatePicker) {
                        MakeDatePickerDialog(
                            onDateSelected = { date = it },
                            onDismiss = { showDatePicker = false }
                        )
                    }

                    MakeButton(
                        text = R.string.search,
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 16.dp)
                            .fillMaxWidth(0.6f)
                            .height(50.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            viewModel.setOption(stationOne, stationTwo, date)
                            viewModel.getData()
                            navController.navigate("schedule_screen")
                        }
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                            .padding(vertical = 10.dp)
                    )

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
                            .align(Alignment.CenterHorizontally)
                    )

                    MakeButton(
                        text = R.string.search,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(0.6f)
                            .height(50.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            trainInfoViewModel.setTrain(trainInfo)
                            trainInfoViewModel.getData()
                            navController.navigate("train_info")
                        }
                    )
                }
            }
        }
    }
}