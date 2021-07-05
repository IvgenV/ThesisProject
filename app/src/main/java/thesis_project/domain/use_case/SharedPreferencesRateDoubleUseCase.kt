package thesis_project.domain.use_case

interface SharedPreferencesRateDoubleUseCase {
    fun add(key: String, rate: Double)
    fun take(key: String): Double
}