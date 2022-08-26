package thesis_project.domain.use_case

interface SharedPreferencesThemeUseCase {

    fun takeCurrentTheme():Int
    fun saveCurrentTheme(theme:Int)

}