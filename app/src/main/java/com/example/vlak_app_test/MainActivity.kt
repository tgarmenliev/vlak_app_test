package com.example.vlak_app_test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vlak_app_test.ui.composables.BottomBarItem
import com.example.vlak_app_test.ui.composables.MakeBottomBar
import com.example.vlak_app_test.ui.composables.MakeTopBar
import com.example.vlak_app_test.ui.live.MakeLiveScreen
import com.example.vlak_app_test.ui.live.sampleLiveInfo
import com.example.vlak_app_test.ui.schedule.MakeScheduleScreenSearch
import com.example.vlak_app_test.ui.theme.Vlak_app_testTheme
import com.example.vlak_app_test.viewmodels.guide.Guide
import com.example.vlak_app_test.viewmodels.train_info.*


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

                val sampleBottomBarInfo = listOf(
                    BottomBarItem(
                        title = R.string.home,
                        filledIcon = painterResource(id = R.drawable.home_filled),
                        outlinedIcon = painterResource(id = R.drawable.home_outlined),
                        route = "home"
                    ),
                    BottomBarItem(
                        title = R.string.short_schedule,
                        filledIcon = painterResource(id = R.drawable.schedule_filled),
                        outlinedIcon = painterResource(id = R.drawable.schedule_outlined),
                        route = "schedule"
                    ),
                    BottomBarItem(
                        title = R.string.short_live,
                        filledIcon = painterResource(id = R.drawable.clock_filled),
                        outlinedIcon = painterResource(id = R.drawable.clock_outlined),
                        route = "live"
                    ),
                    BottomBarItem(
                        title = R.string.guide,
                        filledIcon = painterResource(id = R.drawable.guide_filled),
                        outlinedIcon = painterResource(id = R.drawable.guide_outlined),
                        route = "guide"
                    ),
                )

                val sampleGuideInfo = listOf(
                    Guide.GuideTable(
                        title = "Пътуване с влак1",
                        shortDescription = "Пътуване с влак почти дълго1",
                        description = "Пътуване с влак дълго1",
                        image = painterResource(id = R.drawable.train_one)
                    ),
                    Guide.GuideTable(
                        title = "Пътуване с влак2",
                        shortDescription = "Пътуване с влак почти2",
                        description = "Пътуване с влак много дълго2",
                        image = painterResource(id = R.drawable.train_two)
                    ),
                    Guide.GuideTable(
                        title = "Пътуване с 30123",
                        shortDescription = "Пътуване с 30123 уникално",
                        description = "Избери пътуването с 30123 още сега!",
                        image = painterResource(id = R.drawable.train_three)
                    ),
                )

                //MakeBottomBar(items = sampleBottomBarInfo)

                //MakeGuideScreen(data = photos, dataBottomBar = sampleBottomBarInfo)

                Scaffold(
                    bottomBar = {
                        MakeBottomBar(items = sampleBottomBarInfo)
                    }
                ) {
                    MakeScheduleScreenSearch()
                    //MakeLiveSearchScreen()
                    //MakeGuideScreen(data = sampleGuideInfo)
                    //MakeHomescreen()
                }

//                Scaffold(
//                    topBar = {
//                        MakeTopBar(titleText = R.string.schedule)
//                    }
//                ) {
//                    //MakeScheduleScreenSec(data = sampleScheduleInfo)
////                    MakeScheduleOptionScreenSec(
////                        onAddToTripsButtonPressed = { /*TODO*/ },
////                        getTrainInfo = { /*TODO*/ },
////                        data = sampleScheduleInfo.options[1],
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