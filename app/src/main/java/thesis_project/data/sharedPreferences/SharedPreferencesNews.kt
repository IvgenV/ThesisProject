package thesis_project.data.sharedPreferences

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import thesis_project.domain.repository.SharedPreferencesNewsRepository

class SharedPreferencesNews(context: Context) : SharedPreferencesNewsRepository {

    companion object {
        private const val NEWS_SHARED_PREFERENCES = "NEWS_SHARED_PREFERENCES"
    }

    private val sharedPreferences =
        context.getSharedPreferences(NEWS_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    private val gson = GsonBuilder().create()

    override fun check(title: String, key: String): Boolean {
        return loadNews(key).contains(title)
    }

    override fun add(title: String, key: String) {
        val readNews = loadNews(key)
        if (!readNews.contains(title)) {
            readNews.add(title)
            saveNews(key, readNews)
        }
    }

    private fun loadNews(key: String): MutableList<String> {
        val defValue = gson.toJson(mutableListOf<String>())
        val listStr = sharedPreferences.getString(key, defValue)

        val typeToken = object : TypeToken<MutableList<String>>() {}.type
        return gson.fromJson(listStr, typeToken)
    }

    private fun saveNews(key: String, readNews: MutableList<String>) {
        sharedPreferences.edit()
            .putString(key, gson.toJson(readNews))
            .apply()
    }

}