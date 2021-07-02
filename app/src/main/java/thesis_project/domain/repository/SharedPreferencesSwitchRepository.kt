package thesis_project.domain.repository

import android.content.Context

interface SharedPreferencesSwitchRepository {
    fun add(key:String,status:Boolean)
    fun take(key:String):Boolean
}