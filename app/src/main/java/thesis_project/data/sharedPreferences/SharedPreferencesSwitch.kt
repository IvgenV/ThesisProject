package thesis_project.data.sharedPreferences


import android.content.Context
import android.content.SharedPreferences
import thesis_project.domain.repository.SharedPreferencesSwitchRepository

class SharedPreferencesSwitch(val context: Context):SharedPreferencesSwitchRepository {
    private val PREFS_NAME = "kotlincodes"
    private val DEFAULT_VALUE = false
    val sharedPrefSwitch: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun add(key: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPrefSwitch.edit()
        editor.putBoolean(key, status)
        editor.apply()
    }

    override fun take(key: String): Boolean {
        return  sharedPrefSwitch.getBoolean(key,DEFAULT_VALUE)
    }

}