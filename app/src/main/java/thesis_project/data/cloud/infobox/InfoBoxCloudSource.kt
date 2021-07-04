package thesis_project.data.cloud.infobox

import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thesis_project.data.data_base.infobox.InfoBoxData
import thesis_project.domain.repository.InfoBoxCloudRepository

object InfoBoxCloudSource : InfoBoxCloudRepository {

    private const val baseUrl = "https://belarusbank.by/api/infobox/"

    private val loginInterception = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(loginInterception).build()

    private val retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    private val infoBoxApiService = retrofit.create(ApiInfoBoxBelarusBank::class.java)

    override suspend fun getInfoBoxCountry(): Response<List<InfoBoxData>> {
        val list: Response<List<InfoBoxData>>
        withContext(Dispatchers.IO) {
            list = infoBoxApiService.getInfoBoxCountry()
        }
        return list
    }

    override suspend fun getInfoBoxCity(city: String): Response<List<InfoBoxData>> {
        return infoBoxApiService.getInfoBoxCity(city)
    }
}