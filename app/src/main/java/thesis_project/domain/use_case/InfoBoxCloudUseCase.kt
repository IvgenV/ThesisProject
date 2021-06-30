package thesis_project.domain.use_case

import retrofit2.Response
import thesis_project.data.data_base.infobox.InfoBoxData

interface InfoBoxCloudUseCase {

    suspend fun getInfoBoxCountry(): Response<List<InfoBoxData>>
    suspend fun getInfoBoxCity(city:String): Response<List<InfoBoxData>>

}