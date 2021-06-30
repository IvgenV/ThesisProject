package thesis_project.domain.use_case

import kotlinx.coroutines.flow.Flow
import thesis_project.data.data_base.atm.AtmData

interface AtmDbUseCase {

    suspend fun getAtmCountry(): Flow<List<AtmData>>
    suspend fun getAtmCity(city:String):List<AtmData>
    suspend fun addListAtm(atmList:List<AtmData>)

}