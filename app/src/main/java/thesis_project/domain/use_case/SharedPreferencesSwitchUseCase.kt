package thesis_project.domain.use_case

import android.content.Context

interface SharedPreferencesSwitchUseCase {
    fun add(key:String,status:Boolean)
    fun take(key:String):Boolean
}