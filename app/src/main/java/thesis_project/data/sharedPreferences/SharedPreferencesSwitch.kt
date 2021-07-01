package thesis_project.data.sharedPreferences


import android.content.Context
import android.content.SharedPreferences
import thesis_project.domain.repository.SharedPreferencesSwitchRepository

class SharedPreferencesSwitch:SharedPreferencesSwitchRepository {
    private val PREFS_NAME = "kotlincodes"
    private val DEFAULT_VALUE = false
    override suspend fun Add(key: String, status: Boolean,context: Context) {
        val sharedPrefSwitch: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPrefSwitch.edit()
        editor.putBoolean(key, status)
        editor.apply()
    }

    override fun Take(key: String,context: Context): Boolean {
        val sharedPrefSwitch: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return  sharedPrefSwitch.getBoolean(key,DEFAULT_VALUE)
    }

}