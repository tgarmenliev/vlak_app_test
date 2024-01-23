package com.example.vlak_app_test.ui.composables

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.theme.BottomBarContainerColor
import com.example.vlak_app_test.ui.theme.CalendarDarkColor
import com.example.vlak_app_test.ui.theme.ChosenBottomBarColor
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import com.example.vlak_app_test.ui.theme.TextDarkColor
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.ok),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
            headlineContentColor = MaterialTheme.colorScheme.onBackground,
            selectedDayContainerColor = MaterialTheme.colorScheme.tertiary,
            selectedDayContentColor = MaterialTheme.colorScheme.outlineVariant,
            dayContentColor = MaterialTheme.colorScheme.outline,
            disabledDayContentColor = MaterialTheme.colorScheme.tertiaryContainer,
            todayContentColor = MaterialTheme.colorScheme.primary,
            todayDateBorderColor = MaterialTheme.colorScheme.primary,
            weekdayContentColor = MaterialTheme.colorScheme.outline,

        )
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