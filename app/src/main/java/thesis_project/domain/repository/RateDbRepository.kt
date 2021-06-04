package thesis_project.domain.repository

import thesis_project.data.data_base.Rate

interface RateDbRepository {

    suspend fun getRateCountry():List<Rate>
    suspend fun getRateCity(city:String):List<Rate>
    suspend fun addListRate(rateList: List<Rate>)

}