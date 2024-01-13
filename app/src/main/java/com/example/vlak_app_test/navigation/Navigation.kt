package com.example.vlak_app_test.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.vlak_app_test.ui.bottom_bar.BottomBarViewModel
import com.example.vlak_app_test.ui.composables.MakeBottomBar
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.composables.MakeTopBar
import com.example.vlak_app_test.ui.guide.MakeGuideScreen
import com.example.vlak_app_test.ui.home.MakeHomescreen
import com.example.vlak_app_test.ui.live.MakeLiveScreen
import com.example.vlak_app_test.ui.live.MakeLiveScreenOne
import com.example.vlak_app_test.ui.live.MakeLiveSearchScreen
import com.example.vlak_app_test.ui.live.sampleGuideInfo
import com.example.vlak_app_test.ui.live.sampleLiveInfo
import com.example.vlak_app_test.ui.schedule.MakeScheduleOptionScreen
import com.example.vlak_app_test.ui.schedule.MakeScheduleScreen
import com.example.vlak_app_test.ui.schedule.MakeScheduleSearchScreen
import com.example.vlak_app_test.viewmodels.schedule.sampleScheduleInfo

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentSelectedScreen = navController.currentBackStackEntryAsState().value?.destination?.route

    val bottomBar: @Composable () -> Unit = { MakeBottomBar(items = BottomBarViewModel().bottomBarItems, navController) }
    val routes = listOf("home", "schedule_search", "live_search", "guide")

    Scaffold(
        bottomBar = {
            if (routes.contains(currentSelectedScreen)) {
                bottomBar()
            }
        },
    ) {
        Navigation(
            navController = navController,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
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
                MakeScheduleSearchScreen(modifier = modifier, navController = navController)
            }

            composable("schedule_screen") {
                MakeScheduleScreen(data = sampleScheduleInfo)
            }

            composable("schedule_option_screen") {
                MakeScheduleOptionScreen(
                    onAddToTripsButtonPressed = { /*TODO*/ },
                    getTrainInfo = { /*TODO*/ },
                    data = sampleScheduleInfo.options[0],
                    route = "София - Пловдив"
                )
            }
        }

        navigation(
            route = "live_graph",
            startDestination = "live_search"
        ) {
            composable("live_search") {
                MakeLiveSearchScreen(modifier = modifier, navController = navController)
            }

            composable("live") {
                MakeLiveScreenOne(data = sampleLiveInfo)
            }
        }

        composable("guide") {
            MakeGuideScreen(data = sampleGuideInfo, modifier = modifier)
        }
    }
}
