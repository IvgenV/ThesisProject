package thesis_project.data.cloud

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Cloud {

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

}