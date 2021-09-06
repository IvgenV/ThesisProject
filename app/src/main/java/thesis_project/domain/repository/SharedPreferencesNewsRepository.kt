package thesis_project.domain.repository

import com.google.android.material.card.MaterialCardView

interface SharedPreferencesNewsRepository {

    fun checkSharedPreferences(card: MaterialCardView, title: String)
    fun addToSharedPreferences(title: String,key:String)

}