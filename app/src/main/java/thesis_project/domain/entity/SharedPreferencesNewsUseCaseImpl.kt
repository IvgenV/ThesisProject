package thesis_project.domain.entity

import thesis_project.domain.repository.SharedPreferencesNewsRepository
import thesis_project.domain.use_case.SharedPreferencesNewsUseCase

class SharedPreferencesNewsUseCaseImpl(
    private val sharedPreferencesNewsRepository: SharedPreferencesNewsRepository
) : SharedPreferencesNewsUseCase {
    override fun check(title: String, key: String): Boolean {
        return sharedPreferencesNewsRepository.check(title, key)
    }

    override fun add(title: String, key: String) {
        sharedPreferencesNewsRepository.add(title, key)
    }
}