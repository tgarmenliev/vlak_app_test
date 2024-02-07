package com.example.vlak_app_test.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vlak_app_test.room.UserSettingsDao
import kotlinx.coroutines.launch

class SettingsViewModel(
    dao: UserSettingsDao
): ViewModel() {
    fun updateTheme(theme: String) {
        viewModelScope.launch {
            dao.updateTheme(theme)
        }
    }
}