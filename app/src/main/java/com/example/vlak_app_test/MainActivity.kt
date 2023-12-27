package com.example.vlak_app_test

import DatePickerView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vlak_app_test.ui.live.MakeLiveScreen
import com.example.vlak_app_test.ui.live.MakeLiveSearchScreen
import com.example.vlak_app_test.ui.theme.Vlak_app_testTheme
import com.example.vlak_app_test.ui.train_info.MakeTrainInfo
import com.example.vlak_app_test.ui.train_info.MakeTrainInfoScreen
import com.example.vlak_app_test.viewmodels.live.Live
import com.example.vlak_app_test.viewmodels.train_info.*
import java.util.Calendar
import androidx.compose.runtime.mutableStateOf


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
                            direction = "Sofia",
                            trainNum = "1234",
                            trainType = "BV",
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
                            direction = "Sofia",
                            trainNum = "1224",
                            trainType = "PV",
                            time = "16:00",
                        ),
                        Live.Trains(
                            direction = "Sofia",
                            trainNum = "1237",
                            trainType = "BV",
                            time = "19:00",
                        )
                    )
                )


                //MakeTrainInfo(data = sampleTrainInfo)
                //MakeTrainInfoScreen()
                //MakeLiveSearchScreen()
                //MakeLiveScreen(sampleLiveInfo)
                var selectedDate by remember { mutableStateOf(Calendar.getInstance().timeInMillis) }
                DatePickerView(selectedDate, onDateSelected = { selectedDate = it })
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