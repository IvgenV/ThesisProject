package thesis_project.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import thesis_project.domain.repository.SharedPreferencesThemeRepository

class SharedPreferencesTheme(context: Context):SharedPreferencesThemeRepository {

    companion object{
        private const val PREFS_NAME = "THEME"
        private const val KEY = "THEME_KEY"
    }
    private val sharedPreferences:SharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

    override fun takeCurrentTheme(): Int {
        return sharedPreferences.getInt(KEY,1)
    }

    override fun saveCurrentTheme(theme: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY,theme)
        editor.apply()
    }
}