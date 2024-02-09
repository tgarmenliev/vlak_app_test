package com.example.vlak_app_test.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vlak_app_test.room.UserSettings
import com.example.vlak_app_test.room.UserSettingsDao
import kotlinx.coroutines.delay
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

    init {
        retrieveSettings()
    }

    fun saveSettings(id: Int = 1, theme: String, language: String, name: String) {
        viewModelScope.launch {
            settingsState = SettingsState.Loading
            //delay(1000L)
            if (dao.getCount() == 0) {
                dao.insertUserSettings(UserSettings(theme = theme, language = language, name = name))
            } else {
                dao.updateUserSettings(theme, language, name, id)
            }
            val userSettings = dao.getUserSettings()
            settingsState = SettingsState.Success(userSettings)
        }
    }

    fun retrieveSettings() {
        viewModelScope.launch {
            settingsState = SettingsState.Loading
            if (dao.getCount() == 0) {
                dao.insertUserSettings(UserSettings())
            }

            settingsState = try {
                val userSettings = dao.getUserSettings()
                println(userSettings)
                SettingsState.Success(userSettings)
            } catch (e: Exception) {
                SettingsState.Error(e)
            }
        }
    }

    fun getSettings(): UserSettings {
        return settingsState.let {
            if(it is SettingsState.Success) {
                it.data
            } else {
                UserSettings()
            }
        }
    }
}
