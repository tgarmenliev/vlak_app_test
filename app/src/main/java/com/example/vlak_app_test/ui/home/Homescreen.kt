package com.example.vlak_app_test.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.composables.MakeImageHeader

@Composable
fun MakeHomescreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        MakeImageHeader(
            text = R.string.home,
            image = painterResource(id = R.drawable.home_back2),
            modifier = Modifier.fillMaxHeight(0.3f)
        )
    }
}