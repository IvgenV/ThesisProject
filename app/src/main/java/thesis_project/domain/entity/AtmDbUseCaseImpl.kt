package thesis_project.domain.entity

import thesis_project.data.data_base.atm.AtmPojo
import thesis_project.domain.repository.AtmDbRepository
import thesis_project.domain.use_case.AtmDbUseCase

class AtmDbUseCaseImpl(
    val atmDbRepository: AtmDbRepository
):AtmDbUseCase {
    override suspend fun getAtmCountry(): List<AtmPojo> {
        return atmDbRepository.getAtmCountry()
    }

    override suspend fun getAtmCity(city: String): List<AtmPojo> {
        return atmDbRepository.getAtmCity(city)
    }

    override suspend fun addListAtm(atmList: List<AtmPojo>) {
        atmDbRepository.addListAtm(atmList)
    }
}