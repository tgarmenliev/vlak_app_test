package com.example.vlak_app_test

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import java.util.Locale

@Composable
fun updateAppLanguage(languageCode: String) {
    val context = LocalContext.current
    val configuration = Configuration(context.resources.configuration)
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    configuration.setLocale(locale)

    val density = LocalDensity.current.density
    val displayMetrics = context.resources.displayMetrics
    displayMetrics.setTo(displayMetrics)

    // Re-initialize the context with the updated configuration
    context.resources.updateConfiguration(configuration, displayMetrics)
}