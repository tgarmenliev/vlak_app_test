package com.bultrain.vlak_app_test.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bultrain.vlak_app_test.R

@Composable
fun MakeImageHeader(
    modifier: Modifier = Modifier,
    text: Int,
    image: Painter,
    hasButton: Boolean = false,
    buttonIcon: Painter? = null,
    onButtonClicked: () -> Unit = {},
    leftAlignText: Boolean = false
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

            Box(
                modifier = Modifier
                    .size(58.dp)
                    .clickable {
                        onButtonClicked()
                    }
                    .align(Alignment.TopStart)
                    .padding(start = 18.dp, top = 18.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Transparent, shape = CircleShape)
                        .border(
                            width = 2.dp,
                            color = Color.White,
                            shape = CircleShape
                        )
                )

                Icon(
                    painter = buttonIcon!!,
                    contentDescription = "Button icon",
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Center),
                    tint = Color.White
                )
            }
        }
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.headlineLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(if (leftAlignText) Alignment.TopStart else Alignment.BottomEnd)
                .padding(top = 8.dp, start = 16.dp, bottom = 16.dp, end = 16.dp)
        )
    }
}