package thesis_project.data.cloud


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import thesis_project.Rate
import thesis_project.RateList

interface APIServiceBelarusBank {

    @GET("kursExchange?city=Минск")
    fun getRateMinsk(): Call<List<Rate>>

    @GET("kursExchange?city=Брест")
    fun getRateBrest():Call<List<Rate>>

}