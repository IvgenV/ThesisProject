package thesis_project.domain.repository

import retrofit2.Response
import retrofit2.http.Query
import thesis_project.data.data_base.filials.RatePojo
import thesis_project.data.data_base.filials.СoordinatesPojo

interface RateCloudRepository {

    suspend fun getRateCity(city:String):Response<List<RatePojo>>
    suspend fun getRateCountry(): Response<List<RatePojo>>
    suspend fun getFilialsCountry():Response<List<СoordinatesPojo>>

}