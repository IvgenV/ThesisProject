package thesis_project.domain.use_case

interface SharedPreferencesNewsUseCase {
    fun check(title: String, key: String): Boolean
    fun add(title: String, key: String)
}