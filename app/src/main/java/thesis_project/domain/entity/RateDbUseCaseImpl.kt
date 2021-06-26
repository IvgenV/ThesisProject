package thesis_project.domain.entity

import thesis_project.data.data_base.filials.RateFilialPojo
import thesis_project.domain.repository.RateDbRepository
import thesis_project.domain.use_case.RateDbUseCase

class RateDbUseCaseImpl(
    val rateDbRepository: RateDbRepository
): RateDbUseCase {
    override suspend fun getRateCountry(): List<RateFilialPojo> {
        return rateDbRepository.getRateCountry()
    }

    override suspend fun getRateCity(city: String): List<RateFilialPojo> {
        return rateDbRepository.getRateCity(city)
    }

    override suspend fun addListRate(rateList: List<RateFilialPojo>) {
        rateDbRepository.addListRate(rateList)
    }

}