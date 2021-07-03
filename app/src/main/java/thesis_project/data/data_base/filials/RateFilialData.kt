package thesis_project.data.data_base.filials

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

@Entity
data class RateFilialData(@SerializedName("USD_in") val usd_in: Double,
                          @SerializedName("USD_out") val usd_out:Double,
                          @SerializedName("EUR_in") val euro_in:Double,
                          @SerializedName("EUR_out") val euro_out:Double,
                          @SerializedName("RUB_in") val rub_in:Double,
                          @SerializedName("RUB_out") val rub_out:Double,
                          @SerializedName("UAH_in") val uah_in:Double,
                          @SerializedName("UAH_out") val uah_out:Double,
                          @PrimaryKey
                          @SerializedName("filial_id") val id:Int,
                          @SerializedName("filials_text") val filial:String,
                          @SerializedName("home_number") val home:String,
                          @SerializedName("street_type") val streetType:String,
                          @SerializedName("street") val street:String,
                          @SerializedName("name") val name:String,
                          @SerializedName("GPS_X") val latitude:Double,
                          @SerializedName("GPS_Y") val longitude:Double)