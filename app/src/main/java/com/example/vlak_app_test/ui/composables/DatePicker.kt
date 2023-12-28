import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import com.example.vlak_app_test.ui.theme.SecondaryDarkColor
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView(
    selectedDate: Long,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
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
            todayCalendar.timeInMillis = pickedDate
            onDateSelected(pickedDate)
        }
    }

    DatePicker(
        state = datePickerState,
        showModeToggle = false,
        modifier = modifier,
        title = null,
        colors = DatePickerDefaults.colors(
            selectedDayContainerColor = PrimaryDarkColor,
            selectedDayContentColor = Color.White,
            todayDateBorderColor = SecondaryDarkColor,
            todayContentColor = Color.Black
        )
    )
}