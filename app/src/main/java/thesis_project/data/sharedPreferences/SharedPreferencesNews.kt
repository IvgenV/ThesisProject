package thesis_project.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.android.material.card.MaterialCardView
import com.google.gson.GsonBuilder
import thesis_project.ReadNews
import thesis_project.domain.repository.SharedPreferencesNewsRepository

class SharedPreferencesNews(val context: Context): SharedPreferencesNewsRepository {

    private val shPrNews = "NEWS_SHAREDPREFERENCES"
    var readNews = ReadNews(mutableListOf())
    ///val newsTitleKey = ""
    val readNewsEmpty = ReadNews(mutableListOf())

    override fun checkSharedPreferences(title: String,key:String):Boolean {
        val builder = GsonBuilder()
        val gson = builder.create()
        val str = gson.toJson(readNewsEmpty)
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(shPrNews,Context.MODE_PRIVATE)
        readNews = gson.fromJson(sharedPreferences.getString(key,str),ReadNews::class.java)
        return readNews.newsList.contains(title)
    }

    override fun addToSharedPreferences(title: String,key:String) {
        val sharedPreferences:SharedPreferences = context.getSharedPreferences(shPrNews,Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val builder = GsonBuilder()
        val gson = builder.create()
        var listOfTitle = gson.toJson(readNewsEmpty)

        readNews = gson.fromJson(sharedPreferences.getString(key,listOfTitle), ReadNews::class.java)
        if(!readNews.newsList.contains(title)){
            readNews.newsList.add(title)
        }
        listOfTitle = gson.toJson(readNews)
        editor.putString(key,listOfTitle)
        editor.apply()
    }


}