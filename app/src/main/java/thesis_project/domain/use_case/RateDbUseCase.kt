package thesis_project.domain.use_case

import thesis_project.data.data_base.Rate

interface RateDbUseCase {

    suspend fun getRateCountry():List<Rate>
    suspend fun getRateCity(city:String):List<Rate>
    suspend fun addListRate(rateList: List<Rate>)

}