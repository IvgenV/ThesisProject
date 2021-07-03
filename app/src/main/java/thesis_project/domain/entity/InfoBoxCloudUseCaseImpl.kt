package thesis_project.domain.entity

import retrofit2.Response
import thesis_project.data.data_base.infobox.InfoBoxData
import thesis_project.domain.repository.InfoBoxCloudRepository
import thesis_project.domain.use_case.InfoBoxCloudUseCase

class InfoBoxCloudUseCaseImpl(
    val infoBoxCloudRepository: InfoBoxCloudRepository
): InfoBoxCloudUseCase {

    override suspend fun getInfoBoxCountry(): Response<List<InfoBoxData>> {
        return infoBoxCloudRepository.getInfoBoxCountry()
    }

    override suspend fun getInfoBoxCity(city: String): Response<List<InfoBoxData>> {
        return infoBoxCloudRepository.getInfoBoxCity(city)
    }
}