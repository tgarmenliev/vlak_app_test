package com.example.vlak_app_test.ui.guide

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.vlak_app_test.ui.error.ErrorScreen
import com.example.vlak_app_test.ui.loading.LoadingScreen

@Composable
fun MakeGuideScreen(
    modifier: Modifier = Modifier,
    viewModel: GuideViewModel,
    navController: NavController
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
                    data = viewModel.getAllTopics(),
                    onCardClick = {
                        viewModel.setSelectedTopic(it)
                        viewModel.getGuideTopic()
                        viewModel.setCurrentTopic(it)
                        navController.navigate("guide_more_info")
                    },
                    viewModel = viewModel
                )
            }
        }
        is GuideState.Error -> {
            ErrorScreen(error = guideState.error, modifier = Modifier.fillMaxSize())
            println(guideState.error)
        }
    }

}