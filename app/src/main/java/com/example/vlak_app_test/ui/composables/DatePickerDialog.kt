package com.example.vlak_app_test.ui.composables

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.vlak_app_test.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val todayCalendar = Calendar.getInstance()
    val monthLaterCalendar = todayCalendar.clone() as Calendar
    monthLaterCalendar.add(Calendar.MONTH, 1)

    // Create a DatePickerState that only allows dates from today to a month later
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = todayCalendar.timeInMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val startOfToday = todayCalendar.apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
                return utcTimeMillis in startOfToday..monthLaterCalendar.timeInMillis
            }
        },
        yearRange = todayCalendar.get(Calendar.YEAR)..monthLaterCalendar.get(Calendar.YEAR)
    )

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
                },
            ) {
                Text(
                    text = stringResource(id = R.string.ok),
                )
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
                },
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                )
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
        )
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(Date(millis))
}