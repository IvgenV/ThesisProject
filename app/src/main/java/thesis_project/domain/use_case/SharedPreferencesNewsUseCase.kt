package thesis_project.domain.use_case

import com.google.android.material.card.MaterialCardView

interface SharedPreferencesNewsUseCase {

    fun checkSharedPreferences(card: MaterialCardView, title: String)
    fun addToSharedPreferences(title: String,key:String)

}