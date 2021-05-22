package thesis_project

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thesis_project.data.cloud.APIServiceBelarusBank

object Dependencies {

    val baseUrl = "https://belarusbank.by/api/kursExchange/"

    private val logininterseption = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(logininterseption).build()

    private val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    val apiService = retrofit.create(APIServiceBelarusBank::class.java)


}