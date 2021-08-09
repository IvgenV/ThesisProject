package thesis_project.domain.repository

import kotlinx.coroutines.flow.Flow
import thesis_project.data.data_base.atm.AtmData

interface AtmDbRepository {

    fun getAtmCountryDb(): Flow<List<AtmData>>
    fun getAtmCityDb(city:String):Flow<List<AtmData>>
    suspend fun insertListAtmDb(atmList:List<AtmData>)

}