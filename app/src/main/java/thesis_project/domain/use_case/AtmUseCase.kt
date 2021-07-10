package thesis_project.domain.use_case

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import thesis_project.data.data_base.atm.AtmData

interface AtmUseCase {

    suspend fun getAtmCountryDb():  Flow<List<AtmData>>
    suspend fun getAtmCityDb(city:String):Flow<List<AtmData>>
    suspend fun addListAtmDb(atmList:List<AtmData>)
    suspend fun getAtmCountryCloud(): Response<List<AtmData>>
    suspend fun getAtmCityCloud(city: String): Response<List<AtmData>>

}