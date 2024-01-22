package com.example.vlak_app_test.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.vlak_app_test.ui.bottom_bar.BottomBarViewModel
import com.example.vlak_app_test.ui.bottom_bar.MakeBottomBar
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vlak_app_test.ui.guide.MakeGuideScreen
import com.example.vlak_app_test.ui.home.MakeHomescreen
import com.example.vlak_app_test.ui.live.LiveViewModel
import com.example.vlak_app_test.ui.live.MakeLiveScreenOne
import com.example.vlak_app_test.ui.live.MakeLiveSearchScreen
import com.example.vlak_app_test.ui.live.sampleGuideInfo
import com.example.vlak_app_test.ui.schedule.MakeScheduleOptionScreen
import com.example.vlak_app_test.ui.schedule.MakeScheduleScreen
import com.example.vlak_app_test.ui.schedule.MakeScheduleSearchScreen
import com.example.vlak_app_test.ui.schedule.ScheduleViewModel
import com.example.vlak_app_test.ui.train_info.MakeTrainInfoScreen
import com.example.vlak_app_test.ui.train_info.TrainInfoViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentSelectedScreen = navController.currentBackStackEntryAsState().value?.destination?.route

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val bottomBar: @Composable () -> Unit = remember {
        {
            MakeBottomBar(
                items = BottomBarViewModel().bottomBarItems, navController,
                selectedItemIndex = selectedItemIndex,
                onItemSelected = { selectedItemIndex = it }
            )
        }
    }
    val routes = listOf("home", "schedule_search", "live_search", "guide")

    val scheduleViewModel = remember { ScheduleViewModel() }
    val liveViewModel = remember { LiveViewModel() }
    val trainInfoViewModel = remember { TrainInfoViewModel() }

    Scaffold(
        bottomBar = {
            if (routes.contains(currentSelectedScreen)) {
                bottomBar()
            }
        },
    ) {
        Navigation(
            navController = navController,
            modifier = Modifier.padding(it),
            scheduleViewModel = scheduleViewModel,
            liveViewModel = liveViewModel,
            trainInfoViewModel = trainInfoViewModel
        )
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    scheduleViewModel: ScheduleViewModel = ScheduleViewModel(),
    liveViewModel: LiveViewModel = LiveViewModel(),
    trainInfoViewModel: TrainInfoViewModel = TrainInfoViewModel(),
) {

    //val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MakeHomescreen(modifier = modifier)
        }

        navigation(
            route = "schedule_graph",
            startDestination = "schedule_search"
        ) {
            composable("schedule_search") {
                MakeScheduleSearchScreen(modifier = modifier, navController = navController, viewModel = scheduleViewModel, trainInfoViewModel = trainInfoViewModel)
            }

            composable("schedule_screen") {
                MakeScheduleScreen(viewModel = scheduleViewModel, navController = navController, onCancelButton = { navController.popBackStack() })
            }

            composable("schedule_option_screen") {
                MakeScheduleOptionScreen(
                    onAddToTripsButtonPressed = { /*TODO*/ },
                    trainInfoViewModel = trainInfoViewModel,
                    viewModel = scheduleViewModel,
                    onCancelButton = { navController.popBackStack() },
                    navController = navController
                )
            }

            composable("train_info") {
                MakeTrainInfoScreen(viewModel = trainInfoViewModel, onCancelButton = { navController.popBackStack() })
            }
        }

        navigation(
            route = "live_graph",
            startDestination = "live_search"
        ) {
            composable("live_search") {
                MakeLiveSearchScreen(modifier = modifier, navController = navController, viewModel = liveViewModel)
            }

            composable("live") {
                MakeLiveScreenOne(viewModel = liveViewModel, onCancelButton = { navController.popBackStack() })
            }
        }

        composable("guide") {
            MakeGuideScreen(data = sampleGuideInfo, modifier = modifier)
        }
    }
}
