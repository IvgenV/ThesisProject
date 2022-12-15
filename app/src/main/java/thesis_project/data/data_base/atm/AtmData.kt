package thesis_project.data.data_base.atm

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity
@JsonClass(generateAdapter = true)
data class AtmData(
    @Json(name = "city_type")val cityType:String,
    @Json(name ="city")val city :String,
    @Json(name ="address_type")val addressType :String,
    @Json(name ="address")val address :String,
    @Json(name ="house")val house :String,
    @Json(name ="work_time")val workTime :String,
    @Json(name ="gps_x")val latitude :Double,
    @Json(name ="gps_y")val longitude :Double,
    @Json(name ="ATM_type")val atmType :String,
    @Json(name ="ATM_error")val workingCapacity :String,
    @PrimaryKey
    val id:String,
    val currency:String,
    val cash_in:String
): Serializable