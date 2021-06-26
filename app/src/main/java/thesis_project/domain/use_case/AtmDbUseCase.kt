package thesis_project.domain.use_case

import thesis_project.data.data_base.atm.AtmPojo

interface AtmDbUseCase {

    suspend fun getAtmCountry():List<AtmPojo>
    suspend fun getAtmCity(city:String):List<AtmPojo>
    suspend fun addListAtm(atmList:List<AtmPojo>)

}