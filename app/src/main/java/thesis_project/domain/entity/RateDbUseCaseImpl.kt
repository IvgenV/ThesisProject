package thesis_project.domain.entity

import thesis_project.data.data_base.filials.RateFilialData
import thesis_project.domain.repository.RateDbRepository
import thesis_project.domain.use_case.RateDbUseCase

class RateDbUseCaseImpl(
    val rateDbRepository: RateDbRepository
): RateDbUseCase {
    override suspend fun getRateCountry(): List<RateFilialData> {
        return rateDbRepository.getRateCountry()
    }

    override suspend fun getRateCity(city: String): List<RateFilialData> {
        return rateDbRepository.getRateCity(city)
    }

    override suspend fun addListRate(rateList: List<RateFilialData>) {
        rateDbRepository.addListRate(rateList)
    }

    override suspend fun deleteRoom(rateList: List<RateFilialData>) {
        rateDbRepository.deleteRoom(rateList)
    }

}