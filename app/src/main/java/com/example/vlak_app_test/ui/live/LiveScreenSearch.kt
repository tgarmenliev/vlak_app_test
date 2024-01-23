package com.example.vlak_app_test.ui.live


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.ui.composables.MakeSimpleInputField
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeImageHeader
import com.example.vlak_app_test.ui.composables.MakeStationInputField
import com.example.vlak_app_test.ui.theme.BackgroundColor

@Composable
fun MakeLiveSearchScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LiveViewModel
) {
    var titleText by rememberSaveable { mutableStateOf("") }
    var checked by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        MakeImageHeader(
            text = R.string.live,
            image = painterResource(id = R.drawable.live_back),
            modifier = Modifier.height(200.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

//            Text(
//                text = stringResource(id = R.string.choose_station),
//                style = MaterialTheme.typography.bodyLarge.copy(
//                    color = Color.Black,
//                    fontWeight = FontWeight.Bold,
//                ),
//                modifier = Modifier
//                    .padding(top = 20.dp)
//            )
//
//            MakeSimpleInputField(
//                modifier = Modifier
//                    .padding(horizontal = 40.dp, vertical = 10.dp),
//                titleText = titleText,
//                hintText = R.string.enter_station_name,
//                keyboardType = KeyboardType.Text,
//                onValueChange = {
//                    titleText = it
//                }
//            )

            Spacer(modifier = Modifier.height(20.dp))

            MakeStationInputField(
                station = titleText,
                onStationSelected = {
                    titleText = it
                },
                hintText = R.string.start_searching,
                labelText = R.string.choose_station,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            )

            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    if (checked) {
                        viewModel.setType("arrivals")
                    } else {
                        viewModel.setType("departures")
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = PrimaryDarkColor,
                    checkedTrackColor = PrimaryDarkColor,
                    uncheckedThumbColor = Color.LightGray,
                    uncheckedTrackColor = Color.LightGray
                ),
            )

            MakeButton(
                text = R.string.next,
                onClick = {
                    if (!viewModel.checkIfStationExists(titleText)) {
                        println("station does not exist")
                    } else {
                        viewModel.setStation(titleText)
                        viewModel.getData()
                        navController.navigate("live")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(
                    PrimaryDarkColor
                )
            )
        }
    }
}