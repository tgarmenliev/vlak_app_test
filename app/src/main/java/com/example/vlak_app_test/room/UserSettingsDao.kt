package com.example.vlak_app_test.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserSettingsDao {

    @Insert
    suspend fun insertUserSettings(userSettings: UserSettings)

    @Query("SELECT * FROM user_settings")
    suspend fun getUserSettings(): UserSettings

    @Query("UPDATE user_settings SET theme = :theme")
    suspend fun updateTheme(theme: String)

    @Query("UPDATE user_settings SET language = :language")
    suspend fun updateLanguage(language: String)

    @Query("UPDATE user_settings SET name = :name")
    suspend fun updateName(name: String)

    @Query("UPDATE user_settings SET theme = :theme, language = :language, name = :name WHERE id = :id")
    suspend fun updateUserSettings(theme: String, language: String, name: String, id: Int = 1)

    @Query("SELECT COUNT(*) FROM user_settings")
    suspend fun getCount(): Int
}