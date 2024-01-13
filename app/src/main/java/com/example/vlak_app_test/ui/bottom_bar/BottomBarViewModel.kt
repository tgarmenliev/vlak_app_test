package com.example.vlak_app_test.ui.bottom_bar

import androidx.lifecycle.ViewModel
import com.example.vlak_app_test.R

class BottomBarViewModel : ViewModel() {
    val bottomBarItems = listOf(
        BottomBarItem(
            title = R.string.home,
            filledIcon = R.drawable.home_filled,
            outlinedIcon = R.drawable.home_outlined,
            route = "home"
        ),
        BottomBarItem(
            title = R.string.short_schedule,
            filledIcon = R.drawable.schedule_filled,
            outlinedIcon = R.drawable.schedule_outlined,
            route = "schedule_graph"
        ),
        BottomBarItem(
            title = R.string.short_live,
            filledIcon = R.drawable.clock_filled,
            outlinedIcon = R.drawable.clock_outlined,
            route = "live_graph"
        ),
        BottomBarItem(
            title = R.string.guide,
            filledIcon = R.drawable.guide_filled,
            outlinedIcon = R.drawable.guide_outlined,
            route = "guide"
        ),
    )
}

data class BottomBarItem(
    val title: Int,
    val filledIcon: Int,
    val outlinedIcon: Int,
    val route: String
)