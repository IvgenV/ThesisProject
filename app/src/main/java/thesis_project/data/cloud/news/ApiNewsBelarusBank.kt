package thesis_project.data.cloud.news

import retrofit2.Response
import retrofit2.http.GET
import thesis_project.data.data_base.news.News

interface ApiNewsBelarusBank {

    @GET("news_info?lang=ru")
    suspend fun getNews(): Response<List<News>>
}