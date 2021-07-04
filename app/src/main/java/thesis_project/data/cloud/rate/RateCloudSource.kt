package thesis_project.data.cloud.rate

import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thesis_project.data.data_base.filials.RateData
import thesis_project.data.data_base.filials.СoordinatesData
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

    override suspend fun getRateCity(city: String): Response<List<RateData>> {
        return rateApiService.getRateCity(city)
    }

    override suspend fun getRateCountry(): Response<List<RateData>> {
        val list:Response<List<RateData>>
        withContext(Dispatchers.IO){
            list = rateApiService.getRateCountry()
        }
        return list
    }

    override suspend fun getFilialsCountry(): Response<List<СoordinatesData>> {
        return rateApiService.getFilialsCountry()
    }

}