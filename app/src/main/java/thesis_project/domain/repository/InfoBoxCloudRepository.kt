package thesis_project.domain.repository

import retrofit2.Response
import retrofit2.http.Query
import thesis_project.data.data_base.infobox.InfoBoxData

interface InfoBoxCloudRepository {

    suspend fun getInfoBoxCountry(): Response<List<InfoBoxData>>
    suspend fun getInfoBoxCity(city:String):Response<List<InfoBoxData>>

}