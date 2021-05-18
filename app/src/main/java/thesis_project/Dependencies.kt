package thesis_project

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thesis_project.data.cloud.APIServiceBelarusBank
import thesis_project.data.cloud.RateCallback
import thesis_project.domain.entity.ExchangeRateBelarusBankImpl
import thesis_project.domain.repository.ExchangeRatesBelarusBankRepository
import thesis_project.domain.use_case.ExchangeRatesBelarusBankUseCase

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

}