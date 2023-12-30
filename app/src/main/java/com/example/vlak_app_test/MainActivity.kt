package com.example.vlak_app_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vlak_app_test.ui.composable_screens.MakeDatePickerScreen
import com.example.vlak_app_test.ui.composable_screens.MakeScheduleScreen
import com.example.vlak_app_test.ui.composable_screens.MakeStationsPickerScreen
import com.example.vlak_app_test.ui.live.MakeLiveScreen
import com.example.vlak_app_test.ui.live.MakeLiveSearchScreen
import com.example.vlak_app_test.ui.theme.Vlak_app_testTheme
import com.example.vlak_app_test.ui.train_info.MakeTrainInfo
import com.example.vlak_app_test.viewmodels.live.Live
import com.example.vlak_app_test.viewmodels.schedule.Schedule
import com.example.vlak_app_test.viewmodels.train_info.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vlak_app_testTheme {

                // Create example variable to test the screen
                val sampleTrainInfo = TrainInfo.TrainInfoTable(
                    trainType = "ПВ",
                    trainNum = "30121",
                    stations = listOf(
                        TrainInfo.StationOnTrainInfo(
                            "Station A",
                            "10:00",
                            "10:15"
                        ),
                        TrainInfo.StationOnTrainInfo(
                            "Station B",
                            "11:00",
                            "11:30"
                        ),
                        TrainInfo.StationOnTrainInfo(
                            "Station C",
                            "12:30",
                            "1:00"
                        )
                    )
                )

                val sampleLiveInfo = Live.LiveTable(
                    station = "Пловдив",
                    trains = listOf(
                        Live.Trains(
                            direction = "София",
                            trainNum = "8602",
                            trainType = "БВ",
                            time = "10:00",
                            isDelayed = true,
                            delayedTime = "10:15",
                            delayInfo = Live.DelayInfo(
                                delayMinutes = 15,
                                delayString = "15 min",
                                delayInfo = "Delayed"
                            )
                        ),
                        Live.Trains(
                            direction = "Бургас",
                            trainNum = "8601",
                            trainType = "БВ",
                            time = "16:00",
                        ),
                        Live.Trains(
                            direction = "Септември",
                            trainNum = "10114",
                            trainType = "ПВ",
                            time = "19:00",
                        )
                    )
                )


                //MakeTrainInfo(data = sampleTrainInfo)
                //SearchTrainInfoScreen()
                //MakeLiveSearchScreen()
                //MakeLiveScreen(sampleLiveInfo)
                //var selectedDate by remember { mutableStateOf(Calendar.getInstance().timeInMillis) }
//                DatePickerView(selectedDate,
//                    onDateSelected = {
//                    selectedDate = it
//                        println("Selected date: $selectedDate")
//                })

                //MakeDatePickerScreen(topBarText = "Schedule", backgroundImage = painterResource(id = R.drawable.schedule_back))

//                MakeStationsPickerScreen(
//                    onStationOneSelected = { /*TODO*/ },
//                    onStationTwoSelected = { /*TODO*/ },
//                    onBackButtonPressed = { /*TODO*/ },
//                    backgroundImage = painterResource(id = R.drawable.schedule_back),
//                    topBarText = "Schedule"
//                )

                val sampleScheduleInfo = Schedule.ScheduleTable(
                    date = "2021-10-10",
                    route = "София - Пловдив",
                    options = listOf(
                        Schedule.Options(
                            duration = "1:30",
                            departureTime = "10:00",
                            arrivalTime = "11:30",
                            departureDate = "2021-10-10",
                            arrivalDate = "2021-10-10",
                            numOfTransfers = 0,
                            trains = listOf(
                                Schedule.Trains(
                                    from = "София",
                                    to = "Пловдив",
                                    depart = "10:00",
                                    arrive = "11:30",
                                    departDate = "2021-10-10",
                                    arriveDate = "2021-10-10",
                                    trainType = "ПВ",
                                    trainNum = "30121",
                                    duration = "1:30",
                                    timeToWaitNext = "0:00"
                                )
                            )
                        ),
                        Schedule.Options(
                            duration = "1:40",
                            departureTime = "12:00",
                            arrivalTime = "13:40",
                            departureDate = "2021-11-10",
                            arrivalDate = "2021-11-10",
                            numOfTransfers = 0,
                            trains = listOf(
                                Schedule.Trains(
                                    from = "София",
                                    to = "Пловдив",
                                    depart = "12:00",
                                    arrive = "13:40",
                                    departDate = "2021-11-10",
                                    arriveDate = "2021-11-10",
                                    trainType = "ПВ",
                                    trainNum = "30123",
                                    duration = "1:40",
                                    timeToWaitNext = "0:00"
                                )
                            )
                        ),
                    )
                )

                MakeScheduleScreen(
                    onBackButtonPressed = { /*TODO*/ },
                    backgroundImage = painterResource(id = R.drawable.schedule_back),
                    data = sampleScheduleInfo
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Vlak_app_testTheme {
        Greeting("Android")
    }
}