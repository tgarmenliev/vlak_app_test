package com.example.vlak_app_test.ui.train_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.vlak_app_test.ui.composables.MakeSimpleInputField
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeTopBar

@Composable
fun SearchTrainInfoScreen(): String {
    var titleText by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        Image(
            painter = painterResource(id = R.drawable.live_back),
            contentDescription = "Backgroud image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        MakeTopBar(
            titleText = "Search train info",
            haveCancelButton = true,
            onCancelButtonPressed = {
                titleText = ""
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center

        ) {

            Text(
                text = "Enter train number:",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .align(Alignment.CenterHorizontally)
            )

            MakeSimpleInputField(
                modifier = Modifier
                    .padding(40.dp),
                titleText = titleText,
                hintText = "Enter train number",
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    titleText = it
                }
            )

            MakeButton(
                text = "Next",
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    PrimaryDarkColor
                )
            )
        }
    }

    return "TrainInfoScreen"
}