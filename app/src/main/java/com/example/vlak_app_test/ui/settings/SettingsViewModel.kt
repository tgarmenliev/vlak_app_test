package com.example.vlak_app_test.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vlak_app_test.room.UserSettings
import com.example.vlak_app_test.room.UserSettingsDao
import kotlinx.coroutines.launch

sealed interface SettingsState {
    object Loading : SettingsState
    data class Success(val data: UserSettings) : SettingsState
    data class Error(val error: Throwable) : SettingsState
}

class SettingsViewModel(
    private val dao: UserSettingsDao
) : ViewModel() {

    var settingsState: SettingsState by mutableStateOf(SettingsState.Loading)

    fun saveSettings(theme: String, language: String, name: String) {
        viewModelScope.launch {
            settingsState = SettingsState.Loading
            dao.updateUserSettings(theme, language, name)
            settingsState = SettingsState.Success(dao.getUserSettings())
        }
    }

    fun getSettings() {
        viewModelScope.launch {
            settingsState = SettingsState.Loading
            settingsState = SettingsState.Success(dao.getUserSettings())
        }
    }
}