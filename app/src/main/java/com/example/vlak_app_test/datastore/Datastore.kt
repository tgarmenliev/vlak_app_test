package com.example.vlak_app_test.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


const val USER_PREFERENCES ="user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)

class DataStoreManager(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun storeDarkMode(isOn: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isOn
        }
    }

    val darkModeFlow: Flow<Boolean?> = dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY]
    }

    companion object {
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }
}

//val context = LocalContext.current
//// Define a DataStore for user preferences
//val dataStore: DataStore<Preferences> = context.createDataStore(name = "user_preferences")
//
//// Define a key for the dark mode
//val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
//
//// Function to store the toggle value
//suspend fun storeDarkMode(isOn: Boolean) {
//    dataStore.edit { preferences ->
//        preferences[DARK_MODE_KEY] = isOn
//    }
//}
//
//// Function to retrieve the dark mode value
//val darkModeFlow: Flow<Boolean?> = dataStore.data.map { preferences ->
//    preferences[DARK_MODE_KEY]
//}