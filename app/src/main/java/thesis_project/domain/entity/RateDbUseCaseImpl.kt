package thesis_project.domain.entity

import thesis_project.data.data_base.Rate
import thesis_project.domain.repository.RateDbRepository
import thesis_project.domain.use_case.RateDbUseCase

class RateDbUseCaseImpl(
    val rateDbRepository: RateDbRepository
): RateDbUseCase {
    override suspend fun getRateCountry(): List<Rate> {
        return rateDbRepository.getRateCountry()
    }

    override suspend fun getRateCity(city: String): List<Rate> {
        return rateDbRepository.getRateCity(city)
    }

    override suspend fun addListRate(rateList: List<Rate>) {
        rateDbRepository.addListRate(rateList)
    }


}