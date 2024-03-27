package com.bultrain.vlak_app_test.ui.bottom_bar

import androidx.lifecycle.ViewModel
import com.bultrain.vlak_app_test.R

class BottomBarViewModel : ViewModel() {
    val bottomBarItems = listOf(
        BottomBarItem(
            title = R.string.home,
            filledIcon = R.drawable.home_filled,
            outlinedIcon = R.drawable.home_outlined,
            route = "home",
            firstScreenRoute = "home"
        ),
        BottomBarItem(
            title = R.string.short_schedule,
            filledIcon = R.drawable.schedule_filled,
            outlinedIcon = R.drawable.schedule_outlined,
            route = "schedule_graph",
            firstScreenRoute = "schedule_search"
        ),
        BottomBarItem(
            title = R.string.short_live,
            filledIcon = R.drawable.clock_filled,
            outlinedIcon = R.drawable.clock_outlined,
            route = "live_graph",
            firstScreenRoute = "live_search"
        ),
        BottomBarItem(
            title = R.string.guide_shorter,
            filledIcon = R.drawable.guide_filled,
            outlinedIcon = R.drawable.guide_outlined,
            route = "guide",
            firstScreenRoute = "guide"
        ),
    )
}

data class BottomBarItem(
    val title: Int,
    val filledIcon: Int,
    val outlinedIcon: Int,
    val route: String,
    val firstScreenRoute: String? = null
)