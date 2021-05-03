package com.example.thesisproject


import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import rx.Single

interface APIService {

    @GET("https://belarusbank.by/api/kursExchange?city=Брест")
    fun getMinsk(): Call<Objects>

}