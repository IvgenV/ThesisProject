package thesis_project.domain.use_case

import thesis_project.data.data_base.news.News

interface NewsDbUseCase{
    suspend fun getNewsList():List<News>
    suspend fun addNews(newsList: List<News>)
}