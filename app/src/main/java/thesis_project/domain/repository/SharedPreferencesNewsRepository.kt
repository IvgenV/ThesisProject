package thesis_project.domain.repository

import com.google.android.material.card.MaterialCardView

interface SharedPreferencesNewsRepository {

    fun checkSharedPreferences(title: String,key:String):Boolean
    fun addToSharedPreferences(title: String,key:String)

}