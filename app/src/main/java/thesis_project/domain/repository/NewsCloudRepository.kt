package thesis_project.domain.repository

import retrofit2.Response
import thesis_project.data.data_base.news.News

interface NewsCloudRepository {
    suspend fun getNews(): Response<List<News>>
}