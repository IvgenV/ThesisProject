package thesis_project.domain.repository

import com.google.android.material.card.MaterialCardView

interface SharedPreferencesNewsRepository {

    fun check(title: String, key:String):Boolean
    fun add(title: String, key:String)

}