package com.example.vlak_app_test.ui.live


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.example.vlak_app_test.ui.composables.MakeSimpleInputField
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeImageHeader
import com.example.vlak_app_test.ui.composables.MakeTopBar

@Composable
fun MakeLiveSearchScreen() {
    var titleText by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        MakeImageHeader(
            text = "Електронно табло",
            image = painterResource(id = R.drawable.live_back),
            modifier = Modifier.fillMaxHeight(0.3f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Choose station:",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
            )

            MakeSimpleInputField(
                modifier = Modifier
                    .padding(horizontal = 40.dp, vertical = 10.dp),
                titleText = titleText,
                hintText = "Enter station name",
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    titleText = it
                }
            )

            MakeButton(
                text = "Next",
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(
                    PrimaryDarkColor
                )
            )
        }
    }
}