package com.bultrain.vlak_app_test.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.bultrain.vlak_app_test.ui.bottom_bar.BottomBarViewModel
import com.bultrain.vlak_app_test.ui.bottom_bar.MakeBottomBar
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bultrain.vlak_app_test.datastore.DataStoreManager
import com.bultrain.vlak_app_test.room.AppDatabase
import com.bultrain.vlak_app_test.ui.guide.GuideViewModel
import com.bultrain.vlak_app_test.ui.guide.MakeGuideMoreInfoScreen
import com.bultrain.vlak_app_test.ui.guide.MakeGuideScreen
import com.bultrain.vlak_app_test.ui.home.Homescreen
import com.bultrain.vlak_app_test.ui.home.HomescreenViewModel
import com.bultrain.vlak_app_test.ui.home.MakeTripsScreen
import com.bultrain.vlak_app_test.ui.live.LiveViewModel
import com.bultrain.vlak_app_test.ui.live.MakeLiveScreenOne
import com.bultrain.vlak_app_test.ui.live.MakeLiveSearchScreen
import com.bultrain.vlak_app_test.ui.schedule.MakeScheduleOptionScreen
import com.bultrain.vlak_app_test.ui.schedule.MakeScheduleScreen
import com.bultrain.vlak_app_test.ui.schedule.MakeScheduleSearchScreen
import com.bultrain.vlak_app_test.ui.schedule.ScheduleViewModel
import com.bultrain.vlak_app_test.ui.settings.MakeSettingsScreen
import com.bultrain.vlak_app_test.ui.train_info.MakeTrainInfoScreen
import com.bultrain.vlak_app_test.ui.train_info.TrainInfoViewModel

@Composable
fun AppNavigation(db: AppDatabase, dataStoreManager: DataStoreManager) {
    val navController = rememberNavController()
    val currentSelectedScreen = navController.currentBackStackEntryAsState().value?.destination?.route
    var isKeyboardVisible = remember { mutableStateOf(false) }

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val bottomBar: @Composable () -> Unit = remember {
        {
            MakeBottomBar(
                items = BottomBarViewModel().bottomBarItems, navController,
                selectedItemIndex = selectedItemIndex,
                onItemSelected = { selectedItemIndex = it },
                isKeyboardVisible = isKeyboardVisible
            )
        }
    }
    val routes = listOf("home", "schedule_search", "live_search", "guide")

    // ViewModels
    val scheduleViewModel = remember { ScheduleViewModel(db.scheduleDao()) }
    val liveViewModel = remember { LiveViewModel(db.stationDao()) }
    val trainInfoViewModel = remember { TrainInfoViewModel(db.trainInfoDao(), db.tripTrainsDao()) }
    val homescreenViewModel = remember { HomescreenViewModel(db.tripDao(), db.tripTrainsDao(), trainInfoViewModel) }
    val guideViewModel = remember { GuideViewModel() }

    homescreenViewModel.deleteAndGetRecentTrips()

    Scaffold(
        bottomBar = {
            if (routes.contains(currentSelectedScreen)) { //!isKeyboardVisible.value &&
                bottomBar()
            }
        },
    ) {
        Navigation(
            navController = navController,
            modifier = Modifier.padding (it),
            scheduleViewModel = scheduleViewModel,
            liveViewModel = liveViewModel,
            trainInfoViewModel = trainInfoViewModel,
            homescreenViewModel = homescreenViewModel,
            guideViewModel = guideViewModel,
            dataStoreManager = dataStoreManager,
            isKeyboardVisible = isKeyboardVisible
        )
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    scheduleViewModel: ScheduleViewModel,
    liveViewModel: LiveViewModel,
    trainInfoViewModel: TrainInfoViewModel,
    homescreenViewModel: HomescreenViewModel,
    guideViewModel: GuideViewModel,
    dataStoreManager: DataStoreManager,
    isKeyboardVisible: MutableState<Boolean>
) {

    // Navigation with NavHosts
    NavHost(navController = navController, startDestination = "home_graph") {
        navigation(
            route = "home_graph",
            startDestination = "home"
        ) {
            composable("home") {
                Homescreen(
                    modifier = modifier,
                    viewModel = homescreenViewModel,
                    onSettingsClick = {
                        navController.navigate("settings")
                    },
                    onNumberClick = {
                        trainInfoViewModel.setCanDownload(false)
                        trainInfoViewModel.getTrainInfoByNumber(it)
                        navController.navigate("train_info")
                    },
                    onClick = {
                        homescreenViewModel.getTrips()
                        navController.navigate("trips")
                    },
                    onExploreClick = {
                        navController.navigate("guide")
                    },
                )
            }

            composable("trips") {
                MakeTripsScreen(
                    viewModel = homescreenViewModel,
                    trainInfoViewModel = trainInfoViewModel,
                    onBackSelected = { navController.popBackStack() },
                    navController = navController
                )
            }

            composable("settings") {
                MakeSettingsScreen(onBackButton = { navController.popBackStack() }, dataStoreManager = dataStoreManager)
            }
        }

        navigation(
            route = "schedule_graph",
            startDestination = "schedule_search"
        ) {
            composable("schedule_search") {
                MakeScheduleSearchScreen(modifier = modifier, navController = navController, viewModel = scheduleViewModel, trainInfoViewModel = trainInfoViewModel, isKeyboardVisible = isKeyboardVisible)
            }

            composable("schedule_screen") {
                MakeScheduleScreen(viewModel = scheduleViewModel, navController = navController, onCancelButton = { navController.popBackStack() })
            }

            composable("schedule_option_screen") {
                MakeScheduleOptionScreen(
                    onAddToTripsButtonPressed = { data, route ->
                        homescreenViewModel.insertTrip(
                            trip = data,
                            route = route
                        )
                    },
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
                MakeLiveSearchScreen(modifier = modifier, navController = navController, viewModel = liveViewModel, isKeyboardVisible = isKeyboardVisible)
            }

            composable("live") {
                MakeLiveScreenOne(viewModel = liveViewModel, onCancelButton = { navController.popBackStack() })
            }
        }

        navigation(
            route = "guide_graph",
            startDestination = "guide"
        ) {
            composable("guide") {
                MakeGuideScreen(viewModel = guideViewModel, modifier = modifier, navController = navController)
            }

            composable("guide_more_info") {
                MakeGuideMoreInfoScreen(
                    viewModel = guideViewModel,
                    onClose = {
                        guideViewModel.removeSuccessTopic()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
