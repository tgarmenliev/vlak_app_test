package com.example.vlak_app_test.ui.composables

import DatePickerView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun MakeDatePickerScreen(
    topBarText: String,
) {
    Column(
        Modifier.fillMaxSize()
    ) {

        MakeTopBar(
            titleText = topBarText,
            haveCancelButton = true
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {

            Text(text = "Select date:",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.Black,
                ),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )

            var selectedDate by remember { mutableStateOf(Calendar.getInstance().timeInMillis) }
            var formattedDate by remember { mutableStateOf(convertMillisToDate(selectedDate)) }

            DatePickerView(
                selectedDate,
                onDateSelected = {
                    selectedDate = it
                    formattedDate = convertMillisToDate(it)
                },
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .padding(0.dp)
                    .align(Alignment.CenterHorizontally)
            )

            MakeButton(
                text = "Next",
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 65.dp, end = 65.dp, bottom = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    PrimaryDarkColor
                ),
                enabled = true
            )

            Spacer(modifier = Modifier.requiredHeight(10.dp))

            Text(
                text = "Date: $formattedDate",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Black,
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(Date(millis))
}