package com.example.vlak_app_test.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.example.vlak_app_test.ui.live.MakeLiveSearchScreen
import com.example.vlak_app_test.ui.live.sampleGuideInfo
import com.example.vlak_app_test.ui.live.sampleLiveInfo
import com.example.vlak_app_test.ui.schedule.MakeScheduleOptionScreenSec
import com.example.vlak_app_test.ui.schedule.MakeScheduleScreenSearch
import com.example.vlak_app_test.ui.schedule.MakeScheduleScreenSec
import com.example.vlak_app_test.viewmodels.schedule.sampleScheduleInfo

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentSelectedScreen = navController.currentBackStackEntryAsState().value?.destination?.route

    val bottomBar: @Composable () -> Unit = { MakeBottomBar(items = BottomBarViewModel().bottomBarItems, navController) }
    val routes = listOf("home", "schedule", "live", "guide")
    val topBar: @Composable () -> Unit = { MakeTopBar(titleText = R.string.schedule) }

    Scaffold(
        bottomBar = {
            if (routes.contains(currentSelectedScreen)) {
                bottomBar()
            }
        },
        topBar = {
            if (!routes.contains(currentSelectedScreen)) {
                topBar()
            }
        }
    ) {
        Navigation(
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun Navigation(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MakeHomescreen()
        }

        navigation(
            route = "schedule",
            startDestination = "schedule_search"
        ) {
            composable("schedule_search") {
                MakeScheduleScreenSearch()
            }

            composable("schedule_option") {
                MakeScheduleOptionScreenSec(
                    onAddToTripsButtonPressed = { /*TODO*/ },
                    getTrainInfo = { /*TODO*/ },
                    data = sampleScheduleInfo.options[0],
                    route = "София - Пловдив"
                )
            }

            composable("schedule") {
                MakeScheduleScreenSec(data = sampleScheduleInfo)
            }
        }

        navigation(
            route = "live",
            startDestination = "live_search"
        ) {
            composable("live_search") {
                MakeLiveSearchScreen()
            }

            composable("live") {
                MakeLiveScreen(data = sampleLiveInfo)
            }
        }

        composable("guide") {
            MakeGuideScreen(data = sampleGuideInfo)
        }
    }
}