package thesis_project.domain.repository

import retrofit2.Response
import thesis_project.data.data_base.atm.AtmPojo


interface AtmCloudRepository {

    suspend fun getAtmCountry(): Response<List<AtmPojo>>
    suspend fun getAtmCity(city: String): Response<List<AtmPojo>>

}