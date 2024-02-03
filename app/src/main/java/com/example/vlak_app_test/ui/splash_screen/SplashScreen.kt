package com.example.vlak_app_test.ui.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.vlak_app_test.R
import com.example.vlak_app_test.ui.home.HomeState
import com.example.vlak_app_test.ui.home.HomescreenViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashScreenFinished: () -> Unit,
    homescreenViewModel: HomescreenViewModel
) {
    LaunchedEffect(key1 = true) {
        delay(2000)
        if (homescreenViewModel.homeState is HomeState.Success) {
            onSplashScreenFinished()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "app logo",
            modifier = Modifier.align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
    }
}