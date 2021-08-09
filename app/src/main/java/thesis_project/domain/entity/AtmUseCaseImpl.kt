package thesis_project.domain.entity

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import thesis_project.data.data_base.atm.AtmData
import thesis_project.domain.repository.AtmCloudRepository
import thesis_project.domain.repository.AtmDbRepository
import thesis_project.domain.use_case.AtmUseCase

class AtmUseCaseImpl(
    val atmDbRepository:AtmDbRepository,
    val atmCloudRepository: AtmCloudRepository
): AtmUseCase {

    override suspend fun getAtmCountryCloud():Response<List<AtmData>>{
        return atmCloudRepository.getAtmCountry()
    }

    override suspend fun getAtmCityCloud(city: String): Response<List<AtmData>> {
        return atmCloudRepository.getAtmCity(city)
    }

    override fun getAtmCountryDb(): Flow<List<AtmData>> {
        return atmDbRepository.getAtmCountryDb()
    }

    override fun getAtmCityDb(city: String): Flow<List<AtmData>> {
        return atmDbRepository.getAtmCityDb(city)
    }

    override suspend fun addListAtmDb(atmList: List<AtmData>) {
        atmDbRepository.insertListAtmDb(atmList)    }

}