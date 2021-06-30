package thesis_project.domain.repository

import thesis_project.data.data_base.infobox.InfoBoxData

interface InfoBoxDbRepository {

    suspend fun insertListInfoBox(infoBoxList: List<InfoBoxData>)
    suspend fun getInfoBoxCountry(): List<InfoBoxData>
    suspend fun getInfoBoxCity(city: String): List<InfoBoxData>


}