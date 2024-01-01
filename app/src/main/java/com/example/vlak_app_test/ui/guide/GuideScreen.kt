package com.example.vlak_app_test.ui.guide

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.example.vlak_app_test.ui.composables.BottomBarItem
import com.example.vlak_app_test.ui.composables.MakeBottomBar

@Composable
fun MakeGuideScreen(
    modifier: Modifier = Modifier,
    data: List<Painter>,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CarouselScreen(
            modifier = Modifier.fillMaxHeight(0.85f),
            data = data
        )
    }
}