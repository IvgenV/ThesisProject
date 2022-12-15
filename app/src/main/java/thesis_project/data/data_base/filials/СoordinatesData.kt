package thesis_project.data.data_base.filials

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class СoordinatesData(
    @Json(name = "filial_id") val id: String,
    @Json(name = "GPS_X") val latitude: String,
    @Json(name = "GPS_Y") val longitude: String
): Serializable