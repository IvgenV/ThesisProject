package thesis_project.domain.repository

import retrofit2.Response
import thesis_project.data.data_base.filials.RateData
import thesis_project.data.data_base.filials.СoordinatesData

interface RateCloudRepository {

    suspend fun getRateCity(city:String):Response<List<RateData>>
    suspend fun getRateCountry(): Response<List<RateData>>
    suspend fun getFilialsCountry():Response<List<СoordinatesData>>

}