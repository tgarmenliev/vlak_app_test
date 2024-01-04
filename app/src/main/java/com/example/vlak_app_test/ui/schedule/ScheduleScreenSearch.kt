package com.example.vlak_app_test.ui.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeDatePickerDialog
import com.example.vlak_app_test.ui.composables.MakeImageHeader
import com.example.vlak_app_test.ui.composables.MakeSimpleInputField
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun MakeScheduleScreenSearch(
    modifier: Modifier = Modifier,
) {
    var stationOne by rememberSaveable { mutableStateOf("") }
    var stationTwo by rememberSaveable { mutableStateOf("") }

    val formatter = SimpleDateFormat("dd.MM.yyyy")

    var date by remember {
        mutableStateOf(formatter.format(Date(Calendar.getInstance().timeInMillis)))
    }
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        MakeImageHeader(
            text = R.string.schedule,
            image = painterResource(id = R.drawable.schedule_back),
            modifier = Modifier.fillMaxHeight(0.3f)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MakeSimpleInputField(
                    titleText = stationOne,
                    hintText = R.string.from_station,
                    keyboardType = KeyboardType.Text,
                    onValueChange = {
                        stationOne = it
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                )

                MakeSimpleInputField(
                    titleText = stationTwo,
                    hintText = R.string.to_station,
                    keyboardType = KeyboardType.Text,
                    onValueChange = {
                        stationTwo = it
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                )

                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .border(
                            width = 3.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                    )
                ) {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color.Black
                        ),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Left
                    )
                }

                if (showDatePicker) {
                    MakeDatePickerDialog(
                        onDateSelected = { date = it },
                        onDismiss = { showDatePicker = false }
                    )
                }

                MakeButton(
                    text = R.string.search,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(0.6f)
                        .height(50.dp),
                    onClick = {
                        /*TODO*/
                    }
                )
            }
        }
    }
}