package thesis_project.data.cloud.infobox

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import thesis_project.data.data_base.infobox.InfoBoxData

interface ApiInfoBoxBelarusBank {

    @GET("https://belarusbank.by/api/infobox")
    suspend fun getInfoBoxCountry():Response<List<InfoBoxData>>

    @GET("infobox")
    suspend fun getInfoBoxCity(@Query("city") city:String):Response<List<InfoBoxData>>


}