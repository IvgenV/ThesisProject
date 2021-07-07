package thesis_project.domain.entity

import android.content.Context
import thesis_project.data.sharedPreferences.SharedPreferencesSwitch
import thesis_project.domain.repository.SharedPreferencesSwitchRepository
import thesis_project.domain.use_case.SharedPreferencesSwitchUseCase

class SharedPreferencesSwitchUseCaseImpl(val sharedPreferencesSwitch: SharedPreferencesSwitchRepository) :
    SharedPreferencesSwitchUseCase {
    override fun add(key: String, status: Boolean) {
        sharedPreferencesSwitch.add(key, status)
    }

    override fun take(key: String): Boolean {
        return sharedPreferencesSwitch.take(key)
    }
}