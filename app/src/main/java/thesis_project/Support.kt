package thesis_project

import thesis_project.sealed.CurrencyOperation

object Support {

    fun fromSealedInOut(inOut:Int) =
        when(inOut){
            0 ->{
                CurrencyOperation.Buy
            }
            1 ->{
                CurrencyOperation.Sell
            }
            else -> {
                CurrencyOperation.Error
            }
        }

}