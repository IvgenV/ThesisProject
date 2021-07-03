package thesis_project.domain.use_case

import retrofit2.Response
import thesis_project.data.data_base.filials.RateData
import thesis_project.data.data_base.filials.СoordinatesData

interface RateCloudUseCase {

    suspend fun getRateCity(city:String): Response<List<RateData>>
    suspend fun getRateCountry(): Response<List<RateData>>
    suspend fun getFilialsCountry(): Response<List<СoordinatesData>>

}