package thesis_project.domain.entity

import kotlinx.coroutines.flow.Flow
import thesis_project.data.data_base.atm.AtmData
import thesis_project.domain.repository.AtmDbRepository
import thesis_project.domain.use_case.AtmDbUseCase

class AtmDbUseCaseImpl(
    val atmDbRepository: AtmDbRepository
):AtmDbUseCase {
    override suspend fun getAtmCountry(): Flow<List<AtmData>> {
        return atmDbRepository.getAtmCountry()
    }

    override suspend fun getAtmCity(city: String): List<AtmData> {
        return atmDbRepository.getAtmCity(city)
    }

    override suspend fun addListAtm(atmList: List<AtmData>) {
        atmDbRepository.insertListAtm(atmList)
    }

}