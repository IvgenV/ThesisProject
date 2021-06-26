package thesis_project.data.data_base.news

import android.content.Context
import android.text.Html
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsData(context: Context) {

    private val db_News = Room.databaseBuilder(context, NewsDataBase::class.java,
        "NewsDatabase").build()

    private val NewsDao = db_News.getNewsDao()

    suspend fun getNewsList():List<News> {
        var list: List<News>
        withContext(Dispatchers.IO){
            list = NewsDao.getNews()
        }
        return list
    }
    suspend fun addNews(NewsList: List<News>){

        for (news in  NewsList)
        {
            news.html_ru= Html.fromHtml(news.html_ru).toString()
        }

        withContext(Dispatchers.IO){
            NewsDao.insertListNews(NewsList)
        }
    }
}