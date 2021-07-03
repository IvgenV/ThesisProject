package thesis_project.sealed

sealed class Locality(){

    object Belarus:Locality()
    object City:Locality()

    fun toValue(loc:String):String{
        return when(this){
            Belarus -> "Belarus"
            City -> loc
        }
    }

    companion object{
        fun fromValue(loc:String):Locality{
            return when(loc){
                "Belarus" -> Belarus
                 else -> City
            }
        }
    }

}
