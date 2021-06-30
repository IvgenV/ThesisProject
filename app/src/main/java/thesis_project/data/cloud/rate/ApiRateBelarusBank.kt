package thesis_project.data.cloud.rate


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import thesis_project.data.data_base.filials.СoordinatesData
import thesis_project.data.data_base.filials.RateData

interface ApiRateBelarusBank {

    @GET("kursExchange")
    suspend fun getRateCity(@Query("city")city:String):Response<List<RateData>>

    @GET("https://belarusbank.by/api/kursExchange")
    suspend fun getRateCountry():Response<List<RateData>>

    @GET("https://belarusbank.by/api/filials_info")
    suspend fun getFilialsCountry():Response<List<СoordinatesData>>

}