package thesis_project.data.cloud


import retrofit2.Response
import retrofit2.http.GET
import thesis_project.data.data_base.Rate

interface APIServiceBelarusBank {

    @GET("kursExchange?city=Минск")
    suspend fun getRateMinsk():Response<List<Rate>>

    @GET("kursExchange?city=Брест")
    suspend fun getRateBrest():Response<List<Rate>>

}