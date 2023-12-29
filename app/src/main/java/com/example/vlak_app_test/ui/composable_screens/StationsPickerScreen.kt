package com.example.vlak_app_test.ui.composable_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeSimpleInputField
import com.example.vlak_app_test.ui.composables.MakeTopBar

@Composable
fun MakeStationsPickerScreen(
    onStationOneSelected: () -> Unit,
    onStationTwoSelected: () -> Unit,
    onBackButtonPressed: (Int) -> Unit,
    modifier: Modifier = Modifier,
    backgroundImage: Painter? = null,
    topBarText: String = "Pick stations",
) {
    var stationOne by rememberSaveable { mutableStateOf("") }
    var stationTwo by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize())
    {
        backgroundImage?.let {
            Image(
                painter = it,
                contentDescription = "Background image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Column(
            Modifier.fillMaxSize()
        ) {

            MakeTopBar(
                titleText = topBarText,
                haveCancelButton = true,
                onCancelButtonPressed = onBackButtonPressed
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "From:",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 40.dp)
                )

                MakeSimpleInputField(
                    titleText = stationOne,
                    hintText = "Enter station name",
                    keyboardType = KeyboardType.Text,
                    onValueChange = {
                        stationOne = it
                    },
                    modifier = Modifier
                        .padding(horizontal = 40.dp, vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "To:",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 40.dp, start = 40.dp, end = 40.dp)
                )

                MakeSimpleInputField(
                    titleText = stationTwo,
                    hintText = "Enter station name",
                    keyboardType = KeyboardType.Text,
                    onValueChange = {
                        stationTwo = it
                    },
                    modifier = Modifier
                        .padding(horizontal = 40.dp, vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )

                MakeButton(
                    text = "Next",
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .align(Alignment.CenterHorizontally),
                    onClick = onStationTwoSelected
                )
            }
        }
    }
}