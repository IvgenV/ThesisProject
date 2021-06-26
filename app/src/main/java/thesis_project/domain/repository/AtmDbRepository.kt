package thesis_project.domain.repository

import thesis_project.data.data_base.atm.AtmPojo

interface AtmDbRepository {

    suspend fun getAtmCountry():List<AtmPojo>
    suspend fun getAtmCity(city:String):List<AtmPojo>
    suspend fun addListAtm(atmList:List<AtmPojo>)

}