package com.bultrain.vlak_app_test.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MakeImageHeader(
    modifier: Modifier = Modifier,
    text: Int,
    image: Painter,
    hasButton: Boolean = false,
    buttonIcon: Painter? = null,
    onButtonClicked: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = image,
            contentDescription = "Top image",
            contentScale = ContentScale.Crop,
        )
        if (hasButton) {
            Image(
                painter = buttonIcon!!,
                contentDescription = "Button icon",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 8.dp)
                    .padding(top = 8.dp)
                    .clickable { onButtonClicked() }
            )
        }
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.headlineLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp)
        )
    }
}