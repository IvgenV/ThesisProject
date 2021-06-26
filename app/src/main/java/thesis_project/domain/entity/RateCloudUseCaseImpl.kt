package thesis_project.domain.entity

import retrofit2.Response
import thesis_project.data.data_base.filials.RatePojo
import thesis_project.data.data_base.filials.СoordinatesPojo
import thesis_project.domain.repository.RateCloudRepository
import thesis_project.domain.use_case.RateCloudUseCase

class RateCloudUseCaseImpl(
    val rateCloudRepository: RateCloudRepository
): RateCloudUseCase {
    override suspend fun getRateCity(city: String): Response<List<RatePojo>> {
        return rateCloudRepository.getRateCity(city)
    }

    override suspend fun getRateCountry(): Response<List<RatePojo>> {
        return rateCloudRepository.getRateCountry()
    }

    override suspend fun getFilialsCountry(): Response<List<СoordinatesPojo>> {
        return rateCloudRepository.getFilialsCountry()
    }

}