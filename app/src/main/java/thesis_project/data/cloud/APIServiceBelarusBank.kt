package thesis_project.data.cloud


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import thesis_project.data.data_base.Filials
import thesis_project.data.data_base.Rate

interface APIServiceBelarusBank {

    @GET("kursExchange")
    suspend fun getRateCity(@Query("city")city:String):Response<List<Rate>>

    @GET("https://belarusbank.by/api/kursExchange")
    suspend fun getRateCountry():Response<List<Rate>>

    @GET("https://belarusbank.by/api/filials_info")
    suspend fun getFilialsCountry():Response<List<Filials>>

}