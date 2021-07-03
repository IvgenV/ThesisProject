package thesis_project.sealed

import thesis_project.Constnsts

sealed class Currency{

    object Dollar:Currency()
    object Euro:Currency()
    object Rubble:Currency()
    object Hryvnia:Currency()
    object CurrencyError:Currency()

    fun toValue():Int{
        return when(this){
            Dollar -> Constnsts.usd
            Euro -> Constnsts.eur
            Rubble -> Constnsts.rub
            Hryvnia -> Constnsts.uah
            CurrencyError -> Constnsts.error
        }
    }

    companion object{

        fun fromValue(value:Int):Currency{
            return when(value){
                Constnsts.usd -> Dollar
                Constnsts.eur -> Euro
                Constnsts.rub -> Rubble
                Constnsts.uah -> Hryvnia
                else -> CurrencyError
            }
        }

    }

}
