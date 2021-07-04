package thesis_project.domain.repository

interface SharedPreferencesRateDoubleRepository {
    fun add(key: String, rate: Double)
    fun take(key: String): Double
}