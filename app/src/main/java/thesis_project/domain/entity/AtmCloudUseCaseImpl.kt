package thesis_project.domain.entity

import retrofit2.Response
import thesis_project.data.data_base.atm.AtmPojo
import thesis_project.domain.repository.AtmCloudRepository
import thesis_project.domain.use_case.AtmCloudUseCase

class AtmCloudUseCaseImpl(
    val atmCloudRepository: AtmCloudRepository
): AtmCloudUseCase {

    override suspend fun getAtmCountry(): Response<List<AtmPojo>> {
        return atmCloudRepository.getAtmCountry()
    }

    override suspend fun getAtmCity(city: String): Response<List<AtmPojo>> {
        return atmCloudRepository.getAtmCity(city)
    }


}