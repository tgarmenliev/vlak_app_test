package com.example.vlak_app_test.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UserSettingsDao {
    @Query("SELECT * FROM user_settings WHERE id = 0")
    suspend fun getUserSettings(): UserSettings

    @Query("UPDATE user_settings SET theme = :theme WHERE id = 0")
    suspend fun updateTheme(theme: String)

    @Query("UPDATE user_settings SET language = :language WHERE id = 0")
    suspend fun updateLanguage(language: String)

    @Query("UPDATE user_settings SET name = :name WHERE id = 0")
    suspend fun updateName(name: String)

    @Query("UPDATE user_settings SET theme = :theme, language = :language, name = :name WHERE id = 0")
    suspend fun updateUserSettings(theme: String, language: String, name: String)
}