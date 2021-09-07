package thesis_project.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import thesis_project.domain.repository.SharedPreferencesNewsRepository

class SharedPreferencesNews(val context: Context): SharedPreferencesNewsRepository {

    private val shPrNews = "NEWS_SHAREDPREFERENCES"
    val typeToken = object : TypeToken<MutableList<String>>(){}.type
    val builder = GsonBuilder()
    val gson = builder.create()
    val str = gson.toJson(mutableListOf<String>())
    val sharedPreferences:SharedPreferences = context.getSharedPreferences(shPrNews,Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    override fun checkSharedPreferences(title: String,key:String):Boolean {
        val readNews:MutableList<String> = gson.fromJson(sharedPreferences.getString(key,str),typeToken)
        return readNews.contains(title)
    }

    override fun addToSharedPreferences(title: String,key:String) {
        val readNews:MutableList<String> = gson.fromJson(sharedPreferences.getString(key,str),typeToken)
        if(!readNews.contains(title)){
            readNews.add(title)
            val listOfTitle = gson.toJson(readNews)
            editor.putString(key,listOfTitle)
            editor.apply()
        }
    }


}