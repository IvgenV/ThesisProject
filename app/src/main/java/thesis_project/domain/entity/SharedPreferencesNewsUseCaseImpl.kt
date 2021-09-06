package thesis_project.domain.entity

import com.google.android.material.card.MaterialCardView
import thesis_project.domain.repository.SharedPreferencesNewsRepository
import thesis_project.domain.use_case.SharedPreferencesNewsUseCase

class SharedPreferencesNewsUseCaseImpl(
    val sharedPreferencesNewsRepository: SharedPreferencesNewsRepository
):SharedPreferencesNewsUseCase {
    override fun checkSharedPreferences(card: MaterialCardView, title: String) {
        sharedPreferencesNewsRepository.checkSharedPreferences(card,title)
    }

    override fun addToSharedPreferences(title: String,key:String) {
        sharedPreferencesNewsRepository.addToSharedPreferences(title,key)
    }
}