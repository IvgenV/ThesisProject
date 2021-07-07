package thesis_project.data.cloud.news

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thesis_project.data.cloud.rate.ApiRateBelarusBank
import thesis_project.data.data_base.news.News
import thesis_project.domain.repository.NewsCloudRepository

object NewsCloudSource:NewsCloudRepository {

    private const val baseUrl = "https://belarusbank.by/api/"

    private val loginInterception = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(loginInterception).build()

    private val retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    private val newsApiService = retrofit.create(ApiNewsBelarusBank::class.java)

    override suspend fun getNews(): Response<List<News>> {
            return newsApiService.getNews()
    }
}