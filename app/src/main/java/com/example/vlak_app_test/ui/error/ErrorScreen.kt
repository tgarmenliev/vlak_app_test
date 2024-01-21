package com.example.vlak_app_test.ui.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.R


@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    error: Throwable
) {
    Column(
        modifier = modifier
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = "Error image",
            modifier = Modifier.size(250.dp)
        )

        Text(
            text = stringResource(id = R.string.error),
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = stringResource(id = R.string.more_error_info),
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.Black,
            ),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )

        Text(
            text = error.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray,
            ),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
    }
}