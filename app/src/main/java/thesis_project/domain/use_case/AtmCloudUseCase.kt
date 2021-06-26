package thesis_project.domain.use_case

import retrofit2.Response
import thesis_project.data.data_base.atm.AtmPojo

interface AtmCloudUseCase {

    suspend fun getAtmCountry(): Response<List<AtmPojo>>
    suspend fun getAtmCity(city: String): Response<List<AtmPojo>>

}