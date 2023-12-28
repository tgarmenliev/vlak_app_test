package com.example.vlak_app_test.ui.composable_screens

import DatePickerView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeTopBar
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

        val configuration = LocalConfiguration.current
        val screenWidthDp = configuration.screenWidthDp

        val datePickerWidth = (screenWidthDp * 0.8).dp

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
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
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(datePickerWidth)
            )

            MakeButton(
                text = "Next",
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 65.dp, end = 65.dp, bottom = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    PrimaryDarkColor
                ),
                enabled = true
            )

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