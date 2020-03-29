package ir.esmaeili.stopcar.repository.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import ir.esmaeili.stopcar.utils.Constants

class PreferencesHelperImpl(private val sharedPreferences: SharedPreferences) :
    PreferencesHelper {


    override fun editSharedPreference(key: String, value: Any) {
        sharedPreferences.edit {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getSharedPreference(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue)!! as T
            is Int -> sharedPreferences.getInt(key, defaultValue) as T
            is Long -> sharedPreferences.getLong(key, defaultValue) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
            else -> return 404 as T
        }
    }

    override fun isIntroPageFinished(): Boolean {
        return sharedPreferences.contains(Constants.INTRO_PREF_KEY)
    }

}