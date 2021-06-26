package thesis_project.domain.repository

import thesis_project.data.data_base.filials.RateFilialPojo

interface RateDbRepository {

    suspend fun getRateCountry():List<RateFilialPojo>
    suspend fun getRateCity(city:String):List<RateFilialPojo>
    suspend fun addListRate(rateList: List<RateFilialPojo>)

}