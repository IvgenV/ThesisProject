package thesis_project.domain.repository

import android.content.Context

interface SharedPreferencesSwitchRepository {
    suspend fun Add(key:String,status:Boolean,context: Context)
    fun Take(key:String,context: Context):Boolean
}