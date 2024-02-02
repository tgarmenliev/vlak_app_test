package com.example.vlak_app_test.ui.guide

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vlak_app_test.R
import com.example.vlak_app_test.models.guide.Guide
import com.example.vlak_app_test.ui.error.ErrorScreen
import com.example.vlak_app_test.ui.live.LiveState
import com.example.vlak_app_test.ui.live.MakeLiveScreen
import com.example.vlak_app_test.ui.loading.LoadingScreen
import com.example.vlak_app_test.ui.top_bar.MakeTopBar

@Composable
fun MakeGuideScreen(
    modifier: Modifier = Modifier,
    viewModel: GuideViewModel
) {
    when (val guideState = viewModel.guideState) {
        is GuideState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
        is GuideState.SuccessTopic -> {

        }
        is GuideState.SuccessAllTopics -> {
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                CarouselScreen(
                    modifier = Modifier,
                    data = viewModel.getAllTopics()
                )
            }
        }
        is GuideState.Error -> {
            ErrorScreen(error = guideState.error, modifier = Modifier.fillMaxSize())
            println(guideState.error)
        }
    }

}