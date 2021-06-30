package thesis_project.domain.entity

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import thesis_project.data.data_base.atm.AtmData
import thesis_project.domain.repository.AtmCloudRepository
import thesis_project.domain.use_case.AtmCloudUseCase

class AtmCloudUseCaseImpl(
    val atmCloudRepository: AtmCloudRepository
): AtmCloudUseCase {

    override suspend fun getAtmCountry():Response<List<AtmData>> {
        return atmCloudRepository.getAtmCountry()
    }

    override suspend fun getAtmCity(city: String): Response<List<AtmData>> {
        return atmCloudRepository.getAtmCity(city)
    }


}