package thesis_project.domain.use_case

import retrofit2.Response
import thesis_project.data.data_base.news.News

interface NewsCloudUseCase {
    suspend fun getNews(): Response<List<News>>
}