package thesis_project.domain.repository

import thesis_project.data.data_base.filials.RateFilialData

interface RateDbRepository {

    suspend fun getRateCountry():List<RateFilialData>
    suspend fun getRateCity(city:String):List<RateFilialData>
    suspend fun addListRate(rateList: List<RateFilialData>)
    suspend fun deleteRoom(rateList:List<RateFilialData>)


}