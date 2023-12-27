package com.example.vlak_app_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vlak_app_test.ui.live.MakeLiveScreen
import com.example.vlak_app_test.ui.theme.Vlak_app_testTheme
import com.example.vlak_app_test.ui.train_info.MakeTrainInfo
import com.example.vlak_app_test.viewmodels.live.Live
import com.example.vlak_app_test.viewmodels.train_info.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Create example variable to test the screen
            val sampleTrainInfo = TrainInfo.TrainInfoTable(
                trainType = "Express",
                trainNum = "1234",
                stations = listOf(
                    TrainInfo.StationOnTrainInfo(
                        "Station AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                        "10:00 AM",
                        "10:15 AM"
                    ),
                    TrainInfo.StationOnTrainInfo(
                        "Station B",
                        "11:00 AM",
                        "11:30 AM"
                    ),
                    TrainInfo.StationOnTrainInfo(
                        "Station C",
                        "12:30 PM",
                        "1:00 PM"
                    )
                )
            )

            val sampleLiveInfo = Live.LiveTable(
                station = "Plovdiv",
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


            MakeTrainInfo(data = sampleTrainInfo)
            //MakeTrainInfoScreen()
            //MakeLiveSearchScreen()
            //MakeLiveScreen(sampleLiveInfo)
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