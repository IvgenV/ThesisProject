package thesis_project.domain.use_case

import com.google.android.material.card.MaterialCardView

interface SharedPreferencesNewsUseCase {

    fun checkSharedPreferences(title: String,key:String):Boolean
    fun addToSharedPreferences(title: String,key:String)

}