package thesis_project.domain.entity

import thesis_project.domain.repository.SharedPreferencesThemeRepository
import thesis_project.domain.use_case.SharedPreferencesThemeUseCase

class SharedPreferencesThemeUseCaseImpl(
    val sharedPreferencesThemeRepository: SharedPreferencesThemeRepository
    ): SharedPreferencesThemeUseCase{
    override fun takeCurrentTheme(): Int {
        return sharedPreferencesThemeRepository.takeCurrentTheme()
    }

    override fun saveCurrentTheme(theme: Int) {
        sharedPreferencesThemeRepository.saveCurrentTheme(theme)
    }

}