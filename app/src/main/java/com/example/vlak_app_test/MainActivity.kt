package com.example.vlak_app_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.vlak_app_test.datastore.DataStoreManager
import com.example.vlak_app_test.ui.theme.second.AppTheme2
import com.example.vlak_app_test.navigation.AppNavigation
import com.example.vlak_app_test.room.DatabaseBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        

        setContent {
            val context = LocalContext.current
            val dataStoreManager = remember { DataStoreManager(context) }
            val darkModeState by dataStoreManager.darkModeFlow.collectAsState(initial = false)
            AppTheme2(useDarkTheme = darkModeState == true) {
                val db = DatabaseBuilder.buildDatabase(applicationContext)
                AppNavigation(db = db, dataStoreManager = dataStoreManager)
            }
        }
    }
}