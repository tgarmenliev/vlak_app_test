package com.example.vlak_app_test.ui.live

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.vlak_app_test.R
import com.example.vlak_app_test.models.live.Live
import com.example.vlak_app_test.ui.theme.redLate

@Composable
fun MakeDelayDialog(
    onDismiss: () -> Unit,
    data: Live.Trains,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = Color.White),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.close),
                contentDescription = "Close",
                modifier = Modifier
                    .size(30.dp)
                    .padding(top = 16.dp, start = 16.dp)
                    .clickable { onDismiss() },
                tint = Color.LightGray
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "${data.trainType}${data.trainNum}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    )
                    Text(
                        text = data.direction,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color.Black,
                        ),
                        modifier = Modifier.padding(top = 2.dp, start = 8.dp, end = 8.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = data.time,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = redLate,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(top = 8.dp, end = 8.dp)
                    )
                    Text(
                        text = data.delayedTime,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color.Black,
                            textDecoration = TextDecoration.LineThrough
                        ),
                        modifier = Modifier.padding(top = 2.dp, end = 8.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
            ) {
                Text(
                    text = data.delayInfo.delayString,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp, start = 8.dp, end = 8.dp)
                )
                Text(
                    text = data.delayInfo.delayInfo,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Black,
                    ),
                    modifier = Modifier.padding(top = 2.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
                )
            }
        }
    }
}