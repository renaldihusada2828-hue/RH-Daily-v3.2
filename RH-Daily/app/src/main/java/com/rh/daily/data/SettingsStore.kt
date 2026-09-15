package com.rh.daily.data

import android.content.Context

class SettingsStore(context: Context) {
    private val prefs = context.getSharedPreferences("rh_daily_settings", Context.MODE_PRIVATE)

    fun isDarkMode(): Boolean = prefs.getBoolean(KEY_DARK_MODE, true)
    fun setDarkMode(value: Boolean) = prefs.edit().putBoolean(KEY_DARK_MODE, value).apply()

    fun notificationsEnabled(): Boolean = prefs.getBoolean(KEY_NOTIFICATIONS, false)
    fun setNotificationsEnabled(value: Boolean) = prefs.edit().putBoolean(KEY_NOTIFICATIONS, value).apply()

    companion object {
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_NOTIFICATIONS = "notifications"
    }
}
