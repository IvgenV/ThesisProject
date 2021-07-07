package thesis_project.domain.entity

import thesis_project.domain.repository.SharedPreferencesRateDoubleRepository
import thesis_project.domain.use_case.SharedPreferencesRateDoubleUseCase

class SharedPreferencesRateDoubleUseCaseImpl(
    val sharedPreferencesRateDoubleRepository: SharedPreferencesRateDoubleRepository
) : SharedPreferencesRateDoubleUseCase {
    override fun add(key: String, rate: Double) {
        sharedPreferencesRateDoubleRepository.add(key, rate)
    }

    override fun take(key: String): Double {
        return sharedPreferencesRateDoubleRepository.take(key)
    }
}