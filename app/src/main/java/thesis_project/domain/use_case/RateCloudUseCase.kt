package thesis_project.domain.use_case

import retrofit2.Response
import thesis_project.data.data_base.filials.RatePojo
import thesis_project.data.data_base.filials.СoordinatesPojo

interface RateCloudUseCase {

    suspend fun getRateCity(city:String): Response<List<RatePojo>>
    suspend fun getRateCountry(): Response<List<RatePojo>>
    suspend fun getFilialsCountry(): Response<List<СoordinatesPojo>>

}