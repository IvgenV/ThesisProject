package thesis_project.domain.repository

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import thesis_project.data.data_base.atm.AtmData


interface AtmCloudRepository {

    suspend fun getAtmCountry(): Response<List<AtmData>>
    suspend fun getAtmCity(city: String): Response<List<AtmData>>

}