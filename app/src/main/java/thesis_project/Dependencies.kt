package thesis_project

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thesis_project.data.cloud.APIServiceBelarusBank
import thesis_project.data.cloud.NewsCallback
import thesis_project.data.cloud.RateCallback
import thesis_project.domain.entity.ExchangeRateBelarusBankImpl
import thesis_project.domain.entity.NewsBelarusBankImpl
import thesis_project.domain.repository.ExchangeRatesBelarusBankRepository
import thesis_project.domain.repository.NewsBelarusBankRepository
import thesis_project.domain.use_case.ExchangeRatesBelarusBankUseCase
import thesis_project.domain.use_case.NewsBelarusBankUseCase

object Dependencies {

    private val baseUrlRate = "https://belarusbank.by/api/"

    private val logininterseption = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(logininterseption).build()

    private val retrofit = Retrofit.Builder().baseUrl(baseUrlRate)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(okHttpClient)
        .build()

    val apiService = retrofit.create(APIServiceBelarusBank::class.java)

    private val exchangeRateBelarusBank: ExchangeRatesBelarusBankRepository by lazy { RateCallback() }

    fun getExchangeRateBelarusBankUseCase(): ExchangeRatesBelarusBankUseCase =
        ExchangeRateBelarusBankImpl(exchangeRateBelarusBank)

    private val newsBelarusBank:NewsBelarusBankRepository by lazy { NewsCallback() }

    fun getNewsBelarusBankUseCase():NewsBelarusBankUseCase =
        NewsBelarusBankImpl(newsBelarusBank)

}