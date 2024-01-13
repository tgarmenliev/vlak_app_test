package com.example.vlak_app_test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vlak_app_test.navigation.AppNavigation
import com.example.vlak_app_test.ui.theme.Vlak_app_testTheme
import com.example.vlak_app_test.viewmodels.guide.Guide
import com.example.vlak_app_test.viewmodels.schedule.sampleScheduleInfo
import com.example.vlak_app_test.viewmodels.train_info.*


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vlak_app_testTheme {
                AppNavigation()

                // Create example variable to test the screen
//                val sampleTrainInfo = TrainInfo.TrainInfoTable(
//                    trainType = "ПВ",
//                    trainNum = "30121",
//                    stations = listOf(
//                        TrainInfo.StationOnTrainInfo(
//                            "Station A",
//                            "10:00",
//                            "10:15"
//                        ),
//                        TrainInfo.StationOnTrainInfo(
//                            "Station B",
//                            "11:00",
//                            "11:30"
//                        ),
//                        TrainInfo.StationOnTrainInfo(
//                            "Station C",
//                            "12:30",
//                            "1:00"
//                        )
//                    )
//                )

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

//                MakeScheduleScreen(
//                    onBackButtonPressed = { /*TODO*/ },
//                    backgroundImage = painterResource(id = R.drawable.schedule_back),
//                    data = sampleScheduleInfo
//                )

//                MakeScheduleOptionScreen(
//                    onAddToTripsButtonPressed = { /*TODO*/ },
//                    onBackButtonPressed = { /*TODO*/ },
//                    getTrainInfo = { /*TODO*/ },
//                    data = sampleScheduleInfo.options[0],
//                    route = "София - Пловдив",
//                )

//                val sampleBottomBarInfo = listOf(
//                    BottomBarItem(
//                        title = R.string.home,
//                        filledIcon = painterResource(id = R.drawable.home_filled),
//                        outlinedIcon = painterResource(id = R.drawable.home_outlined),
//                        route = "home"
//                    ),
//                    BottomBarItem(
//                        title = R.string.short_schedule,
//                        filledIcon = painterResource(id = R.drawable.schedule_filled),
//                        outlinedIcon = painterResource(id = R.drawable.schedule_outlined),
//                        route = "schedule"
//                    ),
//                    BottomBarItem(
//                        title = R.string.short_live,
//                        filledIcon = painterResource(id = R.drawable.clock_filled),
//                        outlinedIcon = painterResource(id = R.drawable.clock_outlined),
//                        route = "live"
//                    ),
//                    BottomBarItem(
//                        title = R.string.guide,
//                        filledIcon = painterResource(id = R.drawable.guide_filled),
//                        outlinedIcon = painterResource(id = R.drawable.guide_outlined),
//                        route = "guide"
//                    ),
//                )



                //MakeBottomBar(items = sampleBottomBarInfo)

                //MakeGuideScreen(data = photos, dataBottomBar = sampleBottomBarInfo)

//                Scaffold(
//                    bottomBar = {
//                        MakeBottomBar(items = sampleBottomBarInfo)
//                    }
//                ) {
//                    //MakeScheduleScreenSearch()
//                    //MakeLiveSearchScreen()
//                    MakeGuideScreen(data = sampleGuideInfo, modifier = Modifier.padding(it))
//                    //MakeHomescreen()
//                }

//                Scaffold(
//                    topBar = {
//                        MakeTopBar(titleText = R.string.schedule)
//                    }
//                ) {
//                    //MakeScheduleScreenSec(data = sampleScheduleInfo)
////                    MakeScheduleOptionScreenSec(
////                        onAddToTripsButtonPressed = { /*TODO*/ },
////                        getTrainInfo = { /*TODO*/ },
////                        data = sampleScheduleInfo.options[0],
////                        route = "София - Пловдив"
////                    )
//                    MakeLiveScreen(data = sampleLiveInfo)
//                }
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