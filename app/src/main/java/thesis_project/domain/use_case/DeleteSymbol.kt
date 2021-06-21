package thesis_project.domain.use_case

class DeleteSymbol {
    fun deleteSymbol(text:String):String{
        var newtext:String=""
        var key:Boolean=false
        for(char in text){
            if(char == '<') key=true
            if(key==false){
                newtext="$newtext$char"
            }
            if(char == '>') key=false
        }
        return newtext
    }
}