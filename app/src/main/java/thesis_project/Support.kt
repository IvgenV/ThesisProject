package thesis_project

import thesis_project.sealed.SealedInOut

object Support {

    fun fromSealedInOut(inOut:Int) =
        when(inOut){
            0 ->{
                SealedInOut.In
            }
            1 ->{
                SealedInOut.Out
            }
            else -> {
                SealedInOut.Error
            }
        }

}