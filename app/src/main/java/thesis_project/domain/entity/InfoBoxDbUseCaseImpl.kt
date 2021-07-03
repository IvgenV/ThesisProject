package thesis_project.domain.entity

import thesis_project.data.data_base.infobox.InfoBoxData
import thesis_project.domain.repository.InfoBoxDbRepository
import thesis_project.domain.use_case.InfoBoxDbUseCase

class InfoBoxDbUseCaseImpl(
    val infoBoxDbRepository: InfoBoxDbRepository
): InfoBoxDbUseCase {

    override suspend fun insertListInfoBox(infoBoxList: List<InfoBoxData>) {
        infoBoxDbRepository.insertListInfoBox(infoBoxList)
    }

    override suspend fun getInfoBoxCountry(): List<InfoBoxData> {
        return infoBoxDbRepository.getInfoBoxCountry()
    }

    override suspend fun getInfoBoxCity(city: String): List<InfoBoxData> {
        return infoBoxDbRepository.getInfoBoxCity(city)
    }
}