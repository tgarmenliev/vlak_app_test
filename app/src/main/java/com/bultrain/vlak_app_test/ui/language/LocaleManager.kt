package com.bultrain.vlak_app_test.ui.language

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.Locale

class LocaleManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var language: String
        get() = prefs.getString(PREFS_KEY, Locale.getDefault().language) ?: Locale.getDefault().language
        set(value) = prefs.edit().putString(PREFS_KEY, value).apply()

    fun setLocale(c: Context): Context {
        return updateResources(c, language)
    }

    private fun updateResources(context: Context, language: String): Context {
        var locale = Locale(language)
        Locale.setDefault(locale)

        val res = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }

    companion object {
        const val PREFS_NAME = "LocaleManager"
        const val PREFS_KEY = "locale"
    }
}