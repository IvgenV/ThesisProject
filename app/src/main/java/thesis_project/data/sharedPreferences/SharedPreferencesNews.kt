package thesis_project.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import thesis_project.domain.repository.SharedPreferencesNewsRepository

class SharedPreferencesNews(val context: Context): SharedPreferencesNewsRepository {

    private val shPrNews = "NEWS_SHARED_PREFERENCES"
    val typeToken = object : TypeToken<MutableList<String>>(){}.type

    override fun checkSharedPreferences(title: String,key:String):Boolean {
        val readNews:MutableList<String> = fromJsonNews(key)
        return readNews.contains(title)
    }

    override fun addToSharedPreferences(title: String,key:String) {
        val readNews:MutableList<String> = fromJsonNews(key)
        if(!readNews.contains(title)){
            readNews.add(title)
           toJsonNews(key,readNews)
        }
    }

    private fun fromJsonNews(key:String):MutableList<String>{
        val builder = GsonBuilder()
        val gson = builder.create()
        val sharedPreferences:SharedPreferences = context.getSharedPreferences(shPrNews,Context.MODE_PRIVATE)
        return gson.fromJson(sharedPreferences.getString(key,gson.toJson(mutableListOf<String>())),typeToken)
    }

    private fun toJsonNews(key:String,readNews:MutableList<String>){
        val sharedPreferences:SharedPreferences = context.getSharedPreferences(shPrNews,Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val builder = GsonBuilder()
        val gson = builder.create()
        editor.putString(key,gson.toJson(readNews))
        editor.apply()
    }

}