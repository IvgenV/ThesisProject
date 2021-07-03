package thesis_project.sealed

sealed class CurrencyOperation{

    object Buy:CurrencyOperation()
    object Sell:CurrencyOperation()
    object CurrencyOperationError:CurrencyOperation()

    fun toValue():Int{
        return when(this){
            Buy -> 0
            Sell -> 1
            CurrencyOperationError -> -1
        }
    }

    companion object {
        fun fromValue(value:Int):CurrencyOperation{
            return when(value){
                0 -> Buy
                1 -> Sell
                else -> CurrencyOperationError
            }
        }
    }

}
