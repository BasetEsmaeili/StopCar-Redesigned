package ir.esmaeili.stopcar.repository.preferences

interface PreferencesHelper {
    fun editSharedPreference(key: String, value: Any)
    fun <T : Any> getSharedPreference(key: String, defaultValue: T): T
    fun isIntroPageFinished(): Boolean
}