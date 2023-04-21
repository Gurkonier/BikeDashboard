package de.gurkonier.bikedashboard.utils

import android.content.SharedPreferences
import androidx.core.content.*

object PreferencesManager {
    private var sharedPreferences: SharedPreferences? = null

    enum class Keys(val key: String) {
        SECONDS_ENABLED("seconds_enabled"),
        BATTERY_ENABLED("battery_enabled"),
        DATE_ENABLED("date_enabled")
    }

    fun instantiate(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    var secondsEnabled: Boolean
        set(value) {
            sharedPreferences?.edit {
                putBoolean(Keys.SECONDS_ENABLED.key, value)
            }
        }
        get() {
            return sharedPreferences?.getBoolean(Keys.SECONDS_ENABLED.key, false)?: false
        }

    var batteryEnabled: Boolean
        set(value) {
            sharedPreferences?.edit {
                putBoolean(Keys.BATTERY_ENABLED.key, value)
            }
        }
        get() {
            return sharedPreferences?.getBoolean(Keys.BATTERY_ENABLED.key, false)?: false
        }

    var dateEnabled: Boolean
        set(value) {
            sharedPreferences?.edit {
                putBoolean(Keys.DATE_ENABLED.key, value)
            }
        }
        get() {
            return sharedPreferences?.getBoolean(Keys.DATE_ENABLED.key, false)?: false
        }
}