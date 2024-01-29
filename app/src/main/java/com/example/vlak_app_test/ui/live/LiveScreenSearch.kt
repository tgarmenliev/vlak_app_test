package com.example.vlak_app_test.ui.live


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController
import com.example.vlak_app_test.R
import com.example.vlak_app_test.room.SearchedStation
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeImageHeader
import com.example.vlak_app_test.ui.composables.MakeStationInputField
import com.example.vlak_app_test.ui.theme.BackgroundColor

@Composable
fun MakeLiveSearchScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LiveViewModel,
) {
    var titleText by rememberSaveable { mutableStateOf("") }
    var checked by rememberSaveable { mutableStateOf(false) }
    var recentSearched by rememberSaveable { mutableStateOf<List<SearchedStation>>(emptyList()) }

    LaunchedEffect(Unit) {
        viewModel.getRecentSearches()
        recentSearched = viewModel.recentSearches.value
    }

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
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

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

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 2.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                recentSearched.forEach {
                    Text(
                        text = it.stationName,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable {
                                viewModel.setStation(titleText)
                                viewModel.getData()
                                navController.navigate("live")
                            },
                        textDecoration = TextDecoration.Underline,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 2.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = R.string.departures),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(end = 10.dp),
                    fontWeight = if (checked) FontWeight.Normal else FontWeight.Bold
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
                )

                Text(
                    text = stringResource(id = R.string.arrivals),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        //color = Color.Black,
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp),
                    fontWeight = if (checked) FontWeight.Bold else FontWeight.Normal
                )
            }

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
            )
        }
    }
}