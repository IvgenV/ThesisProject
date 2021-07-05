package thesis_project.data.cloud.atm

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import thesis_project.data.data_base.atm.AtmData

interface ApiAtmBelarusBank {

    @GET("https://belarusbank.by/api/atm")
    suspend fun getAtmCountry():Response<List<AtmData>>

    @GET("atm")
    suspend fun getAtmCity(@Query("city") city: String): Response<List<AtmData>>

}