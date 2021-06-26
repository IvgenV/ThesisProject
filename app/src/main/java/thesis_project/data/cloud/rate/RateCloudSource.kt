package thesis_project.data.cloud.rate

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thesis_project.data.data_base.filials.RatePojo
import thesis_project.data.data_base.filials.СoordinatesPojo
import thesis_project.domain.repository.RateCloudRepository

object RateCloudSource: RateCloudRepository {

    private const val baseUrl = "https://belarusbank.by/api/kursExchange/"

    private val loginInterception = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(loginInterception).build()

    private val retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    private val rateApiService = retrofit.create(ApiRateBelarusBank::class.java)

    override suspend fun getRateCity(city: String): Response<List<RatePojo>> {
        return rateApiService.getRateCity(city)
    }

    override suspend fun getRateCountry(): Response<List<RatePojo>> {
        return rateApiService.getRateCountry()
    }

    override suspend fun getFilialsCountry(): Response<List<СoordinatesPojo>> {
        return rateApiService.getFilialsCountry()
    }

}