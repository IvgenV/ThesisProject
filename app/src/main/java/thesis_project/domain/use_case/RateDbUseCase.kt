package thesis_project.domain.use_case

import thesis_project.data.data_base.filials.RateFilialPojo

interface RateDbUseCase {

    suspend fun getRateCountry():List<RateFilialPojo>
    suspend fun getRateCity(city:String):List<RateFilialPojo>
    suspend fun addListRate(rateList: List<RateFilialPojo>)

}