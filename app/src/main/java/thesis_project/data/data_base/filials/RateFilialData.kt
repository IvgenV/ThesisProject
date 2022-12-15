package thesis_project.data.data_base.filials

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity
@JsonClass(generateAdapter = true)
data class RateFilialData(
    @Json(name = "USD_in") val usd_in: Double,
    @Json(name = "USD_out") val usd_out: Double,
    @Json(name = "EUR_in") val euro_in: Double,
    @Json(name = "EUR_out") val euro_out: Double,
    @Json(name = "RUB_in") val rub_in: Double,
    @Json(name = "RUB_out") val rub_out: Double,
    @Json(name = "UAH_in") val uah_in: Double,
    @Json(name = "UAH_out") val uah_out: Double,
    @PrimaryKey
    @Json(name = "filial_id") val id: Int,
    @Json(name = "filials_text") val filial: String,
    @Json(name = "home_number") val home: String,
    @Json(name = "street_type") val streetType: String,
    @Json(name = "street") val street: String,
    @Json(name = "name") val name: String,
    @Json(name = "GPS_X") val latitude: Double,
    @Json(name = "GPS_Y") val longitude: Double
) : Serializable