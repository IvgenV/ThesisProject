package thesis_project.domain.repository

interface SharedPreferencesThemeRepository {

    fun takeCurrentTheme():Int
    fun saveCurrentTheme(theme:Int)

}