package com.example.vlak_app_test.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.composables.MakeImageHeader
import com.example.vlak_app_test.ui.theme.BackgroundColor

@Composable
fun MakeHomescreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            //.background(BackgroundColor)
    ) {
        MakeImageHeader(
            text = R.string.home,
            image = painterResource(id = R.drawable.home_back2),
            modifier = Modifier.height(200.dp)
        )
    }
}