package thesis_project.domain.entity

import com.google.android.material.card.MaterialCardView
import thesis_project.domain.repository.SharedPreferencesNewsRepository
import thesis_project.domain.use_case.SharedPreferencesNewsUseCase

class SharedPreferencesNewsUseCaseImpl(
    val sharedPreferencesNewsRepository: SharedPreferencesNewsRepository
):SharedPreferencesNewsUseCase {
    override fun checkSharedPreferences( title: String,key:String):Boolean {
        return sharedPreferencesNewsRepository.checkSharedPreferences(title,key)
    }

    override fun addToSharedPreferences(title: String,key:String) {
        sharedPreferencesNewsRepository.addToSharedPreferences(title,key)
    }
}