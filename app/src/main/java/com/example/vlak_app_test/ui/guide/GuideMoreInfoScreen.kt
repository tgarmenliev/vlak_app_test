package com.example.vlak_app_test.ui.guide

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import com.example.vlak_app_test.viewmodels.guide.Guide

@Composable
fun MakeGuideMoreInfoScreen(
    modifier: Modifier = Modifier,
    data: Guide.GuideTable,
    onClose: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(color = Color.White)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.close),
            contentDescription = "Close",
            modifier = Modifier
                .size(30.dp)
                .padding(top = 16.dp, start = 16.dp)
                .clickable { onClose() },
            tint = Color.Gray
        )

        Image(
            painter = data.image,
            contentDescription = "Guide image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.26f)
                .padding(top = 16.dp, bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(20.dp))
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = data.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = PrimaryDarkColor,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = data.description,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = PrimaryDarkColor,
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}