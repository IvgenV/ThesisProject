package thesis_project.domain.use_case

import thesis_project.data.data_base.infobox.InfoBoxData

interface InfoBoxDbUseCase {

    suspend fun insertListInfoBox(infoBoxList: List<InfoBoxData>)
    suspend fun getInfoBoxCountry(): List<InfoBoxData>
    suspend fun getInfoBoxCity(city: String): List<InfoBoxData>


}