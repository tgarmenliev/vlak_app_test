package com.example.vlak_app_test.ui.guide

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vlak_app_test.viewmodels.guide.Guide

@Composable
fun MakeGuideScreen(
    modifier: Modifier = Modifier,
    data: List<Guide.GuideTable>,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CarouselScreen(
            modifier = Modifier,
            data = data
        )
    }
}