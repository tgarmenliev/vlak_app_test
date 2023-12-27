import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView(selectedDate: Long, onDateSelected: (Long) -> Unit) {
    val todayCalendar = Calendar.getInstance()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = todayCalendar.timeInMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val todayMillis = todayCalendar.apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
                return utcTimeMillis >= todayMillis
            }
        }
    )

    LaunchedEffect(datePickerState.selectedDateMillis) {
        val pickedDate = datePickerState.selectedDateMillis ?: selectedDate
        if (pickedDate != selectedDate) {
            todayCalendar.timeInMillis = selectedDate
            onDateSelected(selectedDate)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DatePicker(
            state = datePickerState
        )
        /*
        Spacer(
            modifier = Modifier.height(
                32.dp
            )
        )
        Text(
            text = datePickerState.selectedDateMillis?.let {
                convertMillisToDate(it)
            } ?: "No date selected",
            color = Color.Red
        )*/
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}
