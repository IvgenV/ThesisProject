package thesis_project.data.data_base.news

import android.content.Context
import android.text.Html
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import thesis_project.domain.repository.NewsDbRepository

class NewsData(context: Context):NewsDbRepository {

    private val db_News = Room.databaseBuilder(context, NewsDataBase::class.java,
        "NewsDatabase").build()

    private val NewsDao = db_News.getNewsDao()

    override suspend fun getNewsList():List<News> {
        var list: List<News>
        withContext(Dispatchers.IO){
            list = NewsDao.getNews()
        }
        return list
    }
    override suspend fun addNews(newsList: List<News>){

        withContext(Dispatchers.IO){
            NewsDao.insertListNews(newsList)
        }
    }
}