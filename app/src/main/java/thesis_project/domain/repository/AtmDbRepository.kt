package thesis_project.domain.repository

import kotlinx.coroutines.flow.Flow
import thesis_project.data.data_base.atm.AtmData

interface AtmDbRepository {

    suspend fun getAtmCountry(): Flow<List<AtmData>>
    suspend fun getAtmCity(city:String):List<AtmData>
    suspend fun insertListAtm(atmList:List<AtmData>)

}