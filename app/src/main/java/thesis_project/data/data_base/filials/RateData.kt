package thesis_project.data.data_base.filials


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable


@JsonClass(generateAdapter = true)
data class RateData(
    @Json(name = "USD_in") val usd_in: String,
    @Json(name = "USD_out") val usd_out: String,
    @Json(name = "EUR_in") val euro_in: String,
    @Json(name = "EUR_out") val euro_out: String,
    @Json(name = "RUB_in") val rub_in: String,
    @Json(name = "RUB_out") val rub_out: String,
    @Json(name = "UAH_in") val uah_in: String,
    @Json(name = "UAH_out") val uah_out: String,
    @Json(name = "filial_id") val id: String,
    @Json(name = "filials_text") val filial: String,
    @Json(name = "home_number") val home: String,
    @Json(name = "street_type") val streetType: String,
    @Json(name = "street") val street: String,
    @Json(name = "name") val name: String
): Serializable