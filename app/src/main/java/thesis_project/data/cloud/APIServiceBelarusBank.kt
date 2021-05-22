package thesis_project.data.cloud


import retrofit2.Response
import retrofit2.http.GET
import thesis_project.data.data_base.Rate

interface APIServiceBelarusBank {

    @GET("https://belarusbank.by/api/kursExchange")
    suspend fun getRateCountry():Response<List<Rate>>

}