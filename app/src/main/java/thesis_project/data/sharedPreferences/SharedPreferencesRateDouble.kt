package thesis_project.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import thesis_project.domain.repository.SharedPreferencesRateDoubleRepository

class SharedPreferencesRateDouble(val context: Context) : SharedPreferencesRateDoubleRepository {

    private val PREFS_NAME = "kotlincodes"
    private val DEFAULT_VALUE = -1.0F
    val sharedPrefDouble: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun add(key: String, rate: Double) {
        val editor: SharedPreferences.Editor = sharedPrefDouble.edit()
        editor.putFloat(key, rate.toFloat())
        editor.apply()
    }

    override fun take(key: String): Double {
        return sharedPrefDouble.getFloat(key, DEFAULT_VALUE).toDouble()
    }
}