package thesis_project

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thesis_project.data.cloud.APIServiceBelarusBank
import thesis_project.data.data_base.Rate
import thesis_project.data.data_base.RateDbData
import thesis_project.data.data_base.db.RateDataBase
import thesis_project.domain.entity.RateDbUseCaseImpl
import thesis_project.domain.repository.RateDbRepository
import thesis_project.domain.use_case.RateDbUseCase

object Dependencies {

    val baseUrl = "https://belarusbank.by/api/kursExchange/"

    private val logininterseption = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(logininterseption).build()

    private val retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    val apiService = retrofit.create(APIServiceBelarusBank::class.java)

    private fun getRateDbRepository(context: Context): RateDbRepository {
        return RateDbData(context)
    }

    fun getRateDbUseCase(context: Context): RateDbUseCase =
        RateDbUseCaseImpl(getRateDbRepository(context))

    suspend fun getRateCity(city: String): Response<List<Rate>> {
        return apiService.getRateCity(city)
    }

    suspend fun getCountryRate(): Response<List<Rate>> {
        return apiService.getRateCountry()
    }
}