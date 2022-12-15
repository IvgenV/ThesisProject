package thesis_project.data.data_base.infobox

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity
@JsonClass(generateAdapter = true)
data class InfoBoxData(
    @PrimaryKey
    @Json(name = "info_id") val id: String,
    @Json(name = "city_type") val typeCity: String,
    val city: String,
    @Json(name = "address_type") val typeAddress: String,
    @Json(name = "address") val street: String,
    val house: String,
    @Json(name = "install_place") val place: String,
    @Json(name = "location_name_desc") val description: String,
    @Json(name = "work_time") val workTime: String,
    @Json(name = "gps_x") val latitude: String,
    @Json(name = "gps_y") val longitude: String,
    val currency: String,
    @Json(name = "cash_in_exist") val cashIn: String,
    @Json(name = "inf_status") val workingCondition: String
) : Serializable
