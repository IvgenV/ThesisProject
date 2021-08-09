package thesis_project.data.cloud.atm

import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thesis_project.data.data_base.atm.AtmData
import thesis_project.domain.repository.AtmCloudRepository

class AtmCloudSource(val dispatcher: CoroutineDispatcher): AtmCloudRepository {

    private val baseUrl = "https://belarusbank.by/api/atm/"

    private val loginInterception = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(loginInterception).build()

    private val retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    private val atmApiService = retrofit.create(ApiAtmBelarusBank::class.java)

    override suspend fun getAtmCountry(): Response<List<AtmData>> {
        val list:Response<List<AtmData>>
        withContext(dispatcher){
            list = atmApiService.getAtmCountry()
        }
        return list
    }

    override suspend fun getAtmCity(city: String): Response<List<AtmData>> {
        return withContext(dispatcher){
            atmApiService.getAtmCity(city)
        }
    }
}