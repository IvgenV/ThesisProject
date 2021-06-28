package thesis_project.domain.repository

import thesis_project.data.data_base.news.News

interface NewsDbRepository {
    suspend fun getNewsList():List<News>
    suspend fun addNews(newsList: List<News>)
}