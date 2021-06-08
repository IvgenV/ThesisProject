package thesis_project.domain.entity

import com.google.android.gms.maps.model.LatLng
import thesis_project.data.data_base.Data
import thesis_project.data.data_base.Rate
import thesis_project.domain.repository.RateDbRepository
import thesis_project.domain.use_case.RateDbUseCase

class RateDbUseCaseImpl(
    val rateDbRepository: RateDbRepository
): RateDbUseCase {
    override suspend fun getRateCountry(): List<Data> {
        return rateDbRepository.getRateCountry()
    }

    override suspend fun getRateCity(city: String): List<Data> {
        return rateDbRepository.getRateCity(city)
    }

    override suspend fun addListRate(rateList: List<Data>) {
        rateDbRepository.addListRate(rateList)
    }

}