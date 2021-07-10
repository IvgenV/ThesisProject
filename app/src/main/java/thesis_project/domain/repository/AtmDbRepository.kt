package thesis_project.domain.repository

import kotlinx.coroutines.flow.Flow
import thesis_project.data.data_base.atm.AtmData

interface AtmDbRepository {

    suspend fun getAtmCountryDb(): Flow<List<AtmData>>
    suspend fun getAtmCityDb(city:String):Flow<List<AtmData>>
    suspend fun insertListAtmDb(atmList:List<AtmData>)

}