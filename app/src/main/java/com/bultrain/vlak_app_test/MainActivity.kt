package com.bultrain.vlak_app_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.bultrain.vlak_app_test.datastore.DataStoreManager
import com.bultrain.vlak_app_test.ui.theme.AppTheme
import com.bultrain.vlak_app_test.navigation.AppNavigation
import com.bultrain.vlak_app_test.room.DatabaseBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Create a DataStoreManager instance
            val context = LocalContext.current
            val dataStoreManager = remember { DataStoreManager(context) }

            // Collect the dark mode state from the DataStore
            val darkModeState by dataStoreManager.darkModeFlow.collectAsState(initial = false)

            // Pass the dark mode state to the AppTheme
            AppTheme(useDarkTheme = darkModeState == true) {
                // Create a Database instance
                val db = DatabaseBuilder.buildDatabase(applicationContext)

                // Pass the Database and DataStoreManager instances to the AppNavigation and display the UI
                AppNavigation(db = db, dataStoreManager = dataStoreManager)
            }
        }
    }
}