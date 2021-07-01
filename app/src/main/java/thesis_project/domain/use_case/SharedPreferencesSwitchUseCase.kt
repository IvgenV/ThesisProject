package thesis_project.domain.use_case

import android.content.Context

interface SharedPreferencesSwitchUseCase {
    suspend fun Add(key:String,status:Boolean,context: Context)
    fun Take(key:String,context: Context):Boolean
}