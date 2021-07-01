package thesis_project.domain.entity

import android.content.Context
import thesis_project.data.sharedPreferences.SharedPreferencesSwitch
import thesis_project.domain.use_case.SharedPreferencesSwitchUseCase

class SharedPreferencesSwitchUseCaseImpl(val sharedPreferencesSwitch: SharedPreferencesSwitch):SharedPreferencesSwitchUseCase {
    override suspend fun Add(key: String, status: Boolean,context: Context) {
        sharedPreferencesSwitch.Add(key,status,context)
    }

    override fun Take(key: String,context: Context): Boolean {
        return sharedPreferencesSwitch.Take(key,context)
    }
}