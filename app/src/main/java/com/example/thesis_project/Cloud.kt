package com.example.thesisproject

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Cloud : Callback<Objects> {

    companion object {
        val baseUrl = "https://belarusbank.by/api/kursExchange/"
    }

    val logininterseption = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    val okHttpClient = OkHttpClient.Builder().build()


    val retrofit = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(okHttpClient)
        .build()

    var apiService = retrofit.create(APIService::class.java)

    val call: Call<Objects> = apiService.getMinsk()

    fun start() {
        call.enqueue(this)
    }

    override fun onResponse(call: Call<Objects>, response: Response<Objects>) {
        if (response.isSuccessful) {
            val changeList = response.body()
            Log.d("SDSDSDS", changeList!!.usd)
        } else {
            Log.d("SDSDSDS", "ERRRORRR!")
        }
    }

    override fun onFailure(call: Call<Objects>, t: Throwable) {
        t.printStackTrace()
    }

}